package com.chess.card.api.ws.msg;

import com.chess.card.api.ws.WebSocketSessionRef;
import com.chess.card.api.ws.service.WebSocketService;
import lombok.Data;

import java.io.Serializable;

@Data
public class EntityDataSubCtx implements Serializable {
    private final WebSocketService wsService;
    private final ParamsEntity msgEntity;
    protected final WebSocketSessionRef sessionRef;

    public EntityDataSubCtx(WebSocketService wsService, ParamsEntity msgEntity, WebSocketSessionRef sessionRef) {
        this.wsService = wsService;
        this.msgEntity = msgEntity;
        this.sessionRef = sessionRef;
    }
}
