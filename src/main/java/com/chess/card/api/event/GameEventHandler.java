package com.chess.card.api.event;

import com.chess.card.api.bean.room.RoomInfoVo;
import com.chess.card.api.event.bean.RoomEventMessage;
import com.chess.card.api.event.bean.JoinRoomMessage;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.service.IChessRoomService;
import com.chess.card.api.ws.DefaultWsContextService;
import com.chess.card.api.ws.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEventHandler {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private IChessRoomService chessRoomService;

    @Autowired
    private GameRoomEventProcessor gameRoomEventProcessor;

    @Autowired
    private DefaultWsContextService defaultWsContextService;

    public RoomInfoVo addLookerUser(String roomId){

        SecurityUser securityUser = defaultWsContextService.getSecurityUser();

        String sessionId = securityUser.getSessionId();

        String userId = securityUser.getId();

        gameRoomEventProcessor.startRoom(roomId);

        webSocketService.addLookerUser(roomId,sessionId);

        defaultWsContextService.enterGameRoom(userId,roomId);

        RoomInfoVo roomInfo = chessRoomService.getRoomInfo(roomId);

        //进入游戏
        gameRoomEventProcessor.sendMessage(roomId, RoomEventMessage.builder().roomId(roomId).entityDataSubCtx(defaultWsContextService.getEntityDataSubCtx()).build(),EventType.ENTER_ROOM);

        return roomInfo;

    }

    public void joinGame(Integer seatNo){
        SecurityUser securityUser = defaultWsContextService.getSecurityUser();

        String roomId = webSocketService.addRoomUserBySessionId(securityUser.getSessionId());
        //加入游戏
        chessRoomService.joinRoom(roomId,securityUser,seatNo);

        //加入游戏
        gameRoomEventProcessor.sendMessage(roomId, RoomEventMessage.builder().roomId(roomId).entityDataSubCtx(defaultWsContextService.getEntityDataSubCtx()).build(),EventType.JOIN_GAME);
    }

    public void startGame(){
        SecurityUser securityUser = defaultWsContextService.getSecurityUser();
        String userId = securityUser.getId();
        String roomId = webSocketService.getUserRoomId(userId);

        //开始游戏
        chessRoomService.startGame(userId,roomId);

        //加入游戏
        gameRoomEventProcessor.sendMessage(roomId, RoomEventMessage.builder().roomId(roomId).entityDataSubCtx(defaultWsContextService.getEntityDataSubCtx()).build(),EventType.START_GAME);
    }

}