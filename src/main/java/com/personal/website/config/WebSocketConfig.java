package com.personal.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        //create stomp end points where the sockets will be established
        registry.addEndpoint("ws").setAllowedOrigins("*");
        registry.addEndpoint("ws").setAllowedOrigins("*").withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        //create a simple in memory broker for message sub-pub
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("app");

    }
}
