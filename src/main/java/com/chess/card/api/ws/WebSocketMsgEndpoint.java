package com.chess.ws.api.ws;

import org.springframework.web.socket.CloseStatus;

import java.io.IOException;

public interface WebSocketMsgEndpoint {

    void send(WebSocketSessionRef sessionRef, String subscriptionId, String msg) throws IOException;

    void sendPing(WebSocketSessionRef sessionRef, long currentTime) throws IOException;

    void close(WebSocketSessionRef sessionRef, CloseStatus withReason) throws IOException;
}