package com.chess.card.api.event;

import com.chess.card.api.bean.room.RoomInfoVo;
import com.chess.card.api.event.bean.RoomEventMessage;
import com.chess.card.api.service.IChessRoomService;
import com.chess.card.api.ws.service.WebSocketService;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


/**
 *
 */
@Component
public class RoomUserEventHandler implements EventHandler<RoomEvent> {

    @Autowired
    private IChessRoomService chessRoomService;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void onEvent(RoomEvent event, long sequence, boolean endOfBatch) {
        if(event.getEventType() == EventType.ENTER_ROOM ){
            return;
        }
        String roomId = event.getRoomId();
        RoomEventMessage eventMessage = event.getEventMessage();
        RoomInfoVo roomInfo = chessRoomService.getRoomInfo(roomId);
        webSocketService.notifyRoomUsers(roomId,roomInfo);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}