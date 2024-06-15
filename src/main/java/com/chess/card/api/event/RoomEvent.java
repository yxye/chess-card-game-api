package com.chess.card.api.event;


import lombok.Data;

import java.io.Serializable;

@Data
public class RoomEvent implements Serializable {
    private String roomId;
    private Object message;
}