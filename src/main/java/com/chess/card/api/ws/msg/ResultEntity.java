package com.chess.card.api.ws.msg;

import lombok.Data;

@Data
public class ResultEntity<T> extends MsgEntity{
    private T result;

    private boolean status;

    private String errorMsg;


    public ResultEntity(T result,ParamsEntity params) {
        super(params);
        this.status = true;
        this.result = result;
        this.setType("callResult");
    }

    public ResultEntity(String errorMsg,boolean status,ParamsEntity params) {
        super(params);
        this.status = status;
        this.errorMsg = errorMsg;
        this.setType("callResult");
    }


}
