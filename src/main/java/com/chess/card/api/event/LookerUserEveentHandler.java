package com.chess.card.api.event;

import com.chess.card.api.bean.room.RoomInfoVo;
import com.chess.card.api.event.bean.RoomEventMessage;
import com.chess.card.api.service.IChessRoomService;
import com.chess.card.api.ws.service.WebSocketService;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 将消息推送到观看用户
 */
@Component
public class LookerUserEveentHandler implements EventHandler<RoomEvent> {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private IChessRoomService chessRoomService;

    @Override
    public void onEvent(RoomEvent event, long sequence, boolean endOfBatch) throws Exception {
        if(event.getEventType() == EventType.ENTER_ROOM ){
            return;
        }
        String roomId = event.getRoomId();
        RoomEventMessage eventMessage = event.getEventMessage();
        RoomInfoVo roomInfo = chessRoomService.getRoomInfo(roomId);
        webSocketService.notifyLookerUsers(roomId,roomInfo);
    }
}
