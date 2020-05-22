package cn.ac.ict.config;

import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.vo.WebSocketVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;

@ServerEndpoint(value = "/screen", encoders = WebSocketVo.class)
@Component
@Slf4j
public class ScreenWebsocketServer {
    //存放所有连接
    private static Map<String, Session> clients = Maps.newConcurrentMap();

    @OnOpen
    public void onOpen(Session session) {
        log.info("有新的客户端连接了: [{}]", session.getId());
        //将新用户存入在线的组
        clients.put(session.getId(), session);
    }

    /**
     * 客户端关闭
     *
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:[{}]", session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        throw new GlobalException(AppCodeInfo.FAILED.getCode(), throwable.getMessage());
    }

    /**
     * 收到客户端发来消息
     *
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("服务端收到客户端发来的消息: [{}]", message);
        this.sendAll(message);
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    public void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(message);
        }
    }
}
