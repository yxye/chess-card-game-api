package com.chess.card.api.bean.room;

import lombok.Data;

import java.io.Serializable;

@Data
public class GameInfo implements Serializable {

    private String roomId;

    private String status;
}
