package com.sky.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@ServerEndpoint("/ws/{randomId}")
@Slf4j
public class WebSocketEndPoint {

    // 如果不把sessions设为static，加了ServerEndpoint注解的类，每次新的ws连接过来都会创建一个新的实例，无法获取到之前的session
    static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("randomId") String randomId) {
        log.info("websocket "+ randomId + " is open");
        sessions.add(session);
        log.info("当前session数量 "+ sessions.size());
    }

    //连接关闭的时候执行的操作
    @OnClose
    public void onClose(Session session, @PathParam("randomId") String randomId) {
        log.info("websocket "+ randomId + " is close");
        sessions.remove(session);
    }

    public void notifyAdmin(String notice){
        for(Session session : sessions){
            try {
                session.getBasicRemote().sendText(notice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}