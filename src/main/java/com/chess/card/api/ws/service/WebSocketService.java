package com.chess.card.api.ws.service;

import com.chess.card.api.ws.msg.ResultEntity;
import com.chess.card.api.ws.WebSocketSessionRef;
import com.chess.card.api.ws.event.SessionEvent;
import org.springframework.web.socket.CloseStatus;

public interface WebSocketService {

    void handleWebSocketSessionEvent(WebSocketSessionRef sessionRef, SessionEvent sessionEvent);

    void handleWebSocketMsg(WebSocketSessionRef sessionRef, String msg);

    void close(String sessionId, CloseStatus status);


    public <T> void sendWsMsg(String sessionId, String cmdId, ResultEntity<T> result);


    public  <T> void sendWsMsg(WebSocketSessionRef sessionRef, String cmdId, ResultEntity<T> update);
}
