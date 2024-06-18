package com.chess.card.api.ws.service;

import com.chess.card.api.bean.room.RoomInfoVo;
import com.chess.card.api.ws.data.RoomUserData;
import com.chess.card.api.ws.data.WsSessionMetaData;
import com.chess.card.api.ws.msg.ResultEntity;
import com.chess.card.api.ws.WebSocketSessionRef;
import com.chess.card.api.ws.event.SessionEvent;
import org.springframework.web.socket.CloseStatus;

public interface WebSocketService {

    void handleWebSocketSessionEvent(WebSocketSessionRef sessionRef, SessionEvent sessionEvent);

    void handleWebSocketMsg(WebSocketSessionRef sessionRef, String msg);

    void close(String sessionId, CloseStatus status);

    String getUserRoomId(String userId);


    public <T> void sendWsMsg(String sessionId, String cmdId, ResultEntity<T> result);


    public  <T> void sendWsMsg(WebSocketSessionRef sessionRef, String cmdId, ResultEntity<T> update);


    public void addLookerUser(String roomId,String sessionId);

    public void addRoomUser(String roomId,String sessionId);


    public String addRoomUserBySessionId(String sessionId);

    public String getUserRoomIdBySessionId(String sessionId);


    public void notifyLookerUsers(String roomId, RoomInfoVo roomInfoVo);


    public void notifyRoomUsers(String roomId, RoomInfoVo roomInfoVo);
}
