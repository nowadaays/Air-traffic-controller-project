package com.nowadays.office.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.nowadays.common.processor.MessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class OfficeSocketConfig implements WebSocketConfigurer {
    private final MessageConverter messageConverter;
    private final Cache<String , WebSocketSession> sessionCache;
    private final KafkaTemplate<String , String> kafkaTemplate;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new OfficeSocketHandler(messageConverter , sessionCache , kafkaTemplate), "/websocket").setAllowedOrigins("*");
    }
}
