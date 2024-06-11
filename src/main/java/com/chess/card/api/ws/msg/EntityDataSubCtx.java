package com.chess.ws.api.ws.msg;

import com.chess.ws.api.ws.WebSocketSessionRef;
import com.chess.ws.api.ws.service.WebSocketService;
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
