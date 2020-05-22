package cn.ac.ict.component.impl;

import cn.ac.ict.component.RedisComponent;
import cn.ac.ict.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class RedisComponentImpl implements RedisComponent {

    @Resource
    JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成real  key
            String realKey = key;
            String str = jedis.get(realKey);
            return str;
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Object getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成real  key
            String realKey = key;
            String str = jedis.get(realKey);
            Object object = null;
            if (!StringUtils.isEmpty(str)) {
                // 反序列化成对象
                object = SerializeUtil.deserialize(str);
            }
            return object;
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public List<String> mGet(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> list = jedis.mget(keys);
            return list;
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Boolean set(String key, String value) {
        return set(key, value, -1);
    }

    @Override
    public Boolean setObject(String key, Object value, Integer outTime) {
        String realValue = SerializeUtil.serialize(value);
        return set(key, realValue, outTime);
    }

    @Override
    public Boolean set(String key, String value, Integer outTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value_new = value;
            if (value_new == null || value_new.length() < 0) {
                return false;
            }
            //生成real  key
            String realKey = key;
            //过期时间
            int seconds = Integer.parseInt(outTime.toString());
            if (seconds <= 0) {
                jedis.set(realKey, value_new);
            } else {
                jedis.setex(realKey, seconds, value_new);
            }
            return true;
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成real key
            String realKey = key;
            return jedis.exists(realKey);
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成real key
            String realKey = key;
            if (jedis.del(realKey) > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Long incr(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incr(key);
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getAllKey(String perfix) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(perfix);
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Integer expire(String key, Integer seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expire(key, seconds).intValue();
        } catch (Exception e) {
            log.error("redis连接池异常" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
