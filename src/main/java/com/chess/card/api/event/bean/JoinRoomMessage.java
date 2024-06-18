package com.chess.card.api.event.bean;

import com.chess.card.api.ws.msg.EntityDataSubCtx;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 进入游戏消息
 */
@Data
@Builder
public class JoinRoomMessage implements Serializable {
    private String roomId;

    private EntityDataSubCtx entityDataSubCtx;
}
