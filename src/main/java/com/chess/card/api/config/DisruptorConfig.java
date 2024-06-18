package com.chess.card.api.config;

import com.chess.card.api.event.GameRoomEventProcessor;
import com.chess.card.api.event.RoomEvent;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DisruptorConfig {

    @Bean
    public GameRoomEventProcessor gameRoomEventProcessor() {
        return new GameRoomEventProcessor();
    }
}