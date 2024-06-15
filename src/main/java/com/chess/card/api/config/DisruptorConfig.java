package com.chess.card.api.config;

import com.chess.card.api.event.GameRoomEventProcessor;
import com.chess.card.api.event.RoomEvent;
import com.chess.card.api.event.UserEventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {
    private final ConcurrentHashMap<String, Disruptor<RoomEvent>> roomDisruptors = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<UserEventHandler>> roomSubscribers = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final int bufferSize = 1024;

    @Bean
    public GameRoomEventProcessor gameRoomEventProcessor() {
        return new GameRoomEventProcessor(roomDisruptors, roomSubscribers, executor, bufferSize);
    }
}