package com.chess.card.api.ws.msg;

import lombok.Data;

@Data
public class ParamsEntity extends MsgEntity{

    /**
     *
     */
    private Object[] params;


    public <T> ResultEntity<T> buildResult(T result){
        return new ResultEntity<T>(result,this);
    }

    public <T> ResultEntity<T> buildResult(String errorMsg,boolean status){
        return new ResultEntity<T>(errorMsg,status,this);
    }
}
