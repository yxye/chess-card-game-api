package com.chess.card.api.event;

import com.lmax.disruptor.EventHandler;

public class UserEventHandler implements EventHandler<RoomEvent> {
    private final String userId;

    public UserEventHandler(String userId) {
        this.userId = userId;
    }

    @Override
    public void onEvent(RoomEvent event, long sequence, boolean endOfBatch) {
        System.out.println("User " + userId + " received message: " + event.getMessage() + " in room: " + event.getRoomId());
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}