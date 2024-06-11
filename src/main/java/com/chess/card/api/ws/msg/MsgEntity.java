package com.chess.ws.api.ws.msg;

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
     * callMethod|batchCallMethod|callResult
     */
    private String type;

    /**
     *
     */
    private String methodId;

    public MsgEntity(){

    }
    public MsgEntity(ParamsEntity params){
        this.methodId= params.getMethodId();
        this.type= params.getType();
        this.methodName= params.getMethodName();
    }


}
