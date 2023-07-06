package com.gg.tgather.chattingservice.infra.config;

import com.gg.tgather.chattingservice.infra.properties.CustomProperties;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final CustomProperties customProperties;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        List<String> hosts = customProperties.getHosts();
        String[] array = hosts.toArray(new String[0]);
        registry.addEndpoint("/chat/stomp-chat").setAllowedOrigins(array).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat/sub");

        registry.setApplicationDestinationPrefixes("/chat/pub");
    }

}
