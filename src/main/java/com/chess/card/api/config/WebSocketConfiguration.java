package com.chess.card.api.config;

import com.chess.card.api.exception.ErrorCode;
import com.chess.card.api.exception.ThingsboardException;
import com.chess.card.api.handle.TbWebSocketHandler;

import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.ws.AuthChannelInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;


@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    public static final String WS_PLUGIN_PREFIX = "/api/ws/plugins/";
    private static final String WS_PLUGIN_MAPPING = WS_PLUGIN_PREFIX + "**";


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WS_PLUGIN_MAPPING)
                .setAllowedOrigins("*")
                .withSockJS();
    }


    /**
     * 消息代理配置，使用基于内存的消息代理
     * @param registry 消息代理注册表
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/receive");
        // 端点以app开头的消息将会自动路由给@MessageMapping标注的Controller方法上
        //registry.setApplicationDestinationPrefixes("/app");
    }



    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}