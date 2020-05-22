package cn.ac.ict.shiro;

import cn.ac.ict.component.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 针对自定义的ShiroSession的Redis CRUD操作，通过isChanged标识符，确定是否需要调用Update方法
 * 通过配置securityManager在属性cacheManager查找从缓存中查找Session是否存在，如果找不到才调用下面方法
 * Shiro内部相应的组件（DefaultSecurityManager）会自动检测相应的对象（如Realm）是否实现了CacheManagerAware并自动注入相应的CacheManager。
 */
@Slf4j
@Component
public class ShiroSessionDao extends CachingSessionDAO {

    // 保存到Redis中key的前缀 prefix+sessionId
    private String prefix = "";

    // 设置会话的过期时间
    private int seconds = 0;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Resource
    private RedisComponent redisComponent;

    /**
     * 重写CachingSessionDAO中readSession方法，
     * 如果Session中没有登陆信息就调用doReadSession方法从Redis中重读
     */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session session = getCachedSession(sessionId);
        if (ObjectUtils.isEmpty(session) || ObjectUtils.isEmpty(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY))) {
            session = this.doReadSession(sessionId);
            if (ObjectUtils.isEmpty(session)) {
                throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
            } else {
                // 缓存
                cache(session, session.getId());
            }
        }
        return session;
    }

    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        try {
            String key = prefix + sessionId;
            session = (Session) redisComponent.getObject(key);
        } catch (Exception e) {
            log.warn("读取Session失败", e);
        }
        return session;
    }

    /**
     * 如DefaultSessionManager在创建完session后会调用该方法；
     * 如保存到关系数据库/文件系统/NoSQL数据库；即可以实现会话的持久化；
     * 返回会话ID；主要此处返回的ID.equals(session.getId())；
     */
    @Override
    protected Serializable doCreate(Session session) {
        // 创建一个Id并设置给Session
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        try {
            // session由Redis缓存失效决定，这里只是简单标识
            session.setTimeout(seconds);
            redisComponent.setObject(prefix + sessionId, session, seconds);
            log.info("sessionId {} name {} 被创建", sessionId, session.getClass().getName());
        } catch (Exception e) {
            log.warn("创建Session失败", e);
        }
        return sessionId;
    }

    /**
     * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     */
    @Override
    protected void doUpdate(Session session) {
        //如果会话过期/停止 没必要再更新了
        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }
        } catch (Exception e) {
            log.error("ValidatingSession error");
        }
        try {
            try {
                redisComponent.setObject(prefix + session.getId(), session, seconds);
                //logger.info("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
            } catch (Exception e) {
                log.info("sessionId {} name {} 更新异常", session.getId(), session.getClass().getName());
                throw e;
            }
        } catch (Exception e) {
            log.warn("更新Session失败", e);
        }
    }

    /**
     * 删除会话；当会话过期/会话停止（如用户退出时）会调用
     */
    @Override
    protected void doDelete(Session session) {
        try {
            redisComponent.del(prefix + session.getId());
            log.debug("Session {} 被删除", session.getId());
        } catch (Exception e) {
            log.warn("删除Session失败", e);
        }
    }

    /**
     * 删除cache中缓存的Session
     */
    public void uncache(Serializable sessionId) {
        Session session = this.readSession(sessionId);
        super.uncache(session);
        log.info("取消session {} 的缓存", sessionId);
    }
}
