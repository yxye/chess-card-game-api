package com.chess.card.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxParams implements Serializable {
    private String signature;
    private String timestamp;
    private String echostr;
}
