package com.chess.card.api.event;

import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;

/**
 * 处理房间事件，
 * 持久化房间数据
 */
@Component
public class RoomDbEventHandler implements EventHandler<RoomEvent> {

    @Override
    public void onEvent(RoomEvent event, long sequence, boolean endOfBatch) throws Exception {

    }
}
