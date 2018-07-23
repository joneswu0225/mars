package com.jones.mars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(new String[]{"/client", "/user"});
        config.setApplicationDestinationPrefixes(new String[]{"/ws"});
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(new String[]{"/panopipe"}).withSockJS();
    }

    @Bean
    public SocketSessionRegistry SocketSessionRegistry() {
        return new SocketSessionRegistry();
    }

    @Bean
    public STOMPConnectEventListener STOMPConnectEventListener() {
        return new STOMPConnectEventListener();
    }
}
