package com.chess.card.api.event;

import com.lmax.disruptor.RingBuffer;

public class RoomEventProducer {
    private final RingBuffer<RoomEvent> ringBuffer;

    public RoomEventProducer(RingBuffer<RoomEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String roomId, String message) {
        long sequence = ringBuffer.next();
        try {
            RoomEvent event = ringBuffer.get(sequence);
            event.setRoomId(roomId);
            event.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}