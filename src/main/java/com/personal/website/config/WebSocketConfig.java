package com.personal.website.config;

import com.personal.website.repository.UserRepository;
import com.personal.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{

    @Autowired
    UserService userService;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/queue/", "/topic/", "/user/");   // Enables a simple in-memory broker
        registry.setUserDestinationPrefix("/user");


        //   Use this for enabling a Full featured broker like RabbitMQ or ActiveMQ

        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }


//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//
//
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//                if(accessor.getCommand()==null){
//                    return;
//                }
//
//
//
//                switch(accessor.getCommand()){
//                    case CONNECT:
//                    case CONNECTED:
//                        getMessage(message);
//                        userService.toggleUserPresence("Nsimwela", true);
//                        break;
//                    case DISCONNECT:
//                        userService.toggleUserPresence("Nsimwela", false);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//    }
}
