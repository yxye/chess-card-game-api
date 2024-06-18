package com.chess.card.api.ws.msg;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class MsgEntity implements Serializable {
    /**
     * 方法名称
     */
    private String methodName;

    /**
     * callMethod|batchCallMethod|callResult|event
     */
    private String type;

    /**
     *
     */
    private String methodId;

    public MsgEntity(){

    }

    public MsgEntity(String type,String eventName){
        this.type = type;
        this.methodName = eventName;
    }

    public MsgEntity(ParamsEntity params){
        this.methodId= params.getMethodId();
        this.type= params.getType();
        this.methodName= params.getMethodName();
    }


}
