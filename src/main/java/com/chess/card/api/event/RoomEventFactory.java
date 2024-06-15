package com.chess.card.api.event;

import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

@Component
public class RoomEventFactory implements EventFactory<RoomEvent> {
    @Override
    public RoomEvent newInstance() {
        return new RoomEvent();
    }
}