package com.jones.mars.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

public class STOMPConnectEventListener
        implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        String agentId = (String) sha.getNativeHeader("code").get(0);
        String sessionId = sha.getSessionId();
        this.webAgentSessionRegistry.registerSessionId(agentId, sessionId);
    }
}
