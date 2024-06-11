package com.chess.ws.api.ws.api;

import com.chess.ws.api.ws.msg.EntityDataSubCtx;
import com.chess.ws.api.ws.msg.ParamsEntity;
import com.chess.ws.api.ws.msg.ResultEntity;
import com.chess.ws.api.ws.processor.WebsocketServiceScanning;
import com.chess.ws.api.ws.WebSocketSessionRef;
import com.chess.ws.api.ws.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
public class DefaultDataSchedulingService {

    @Autowired
    private WebsocketServiceScanning websocketServiceScanning;


    public <T> void execute(EntityDataSubCtx entityDataSubCtx) {
        ParamsEntity paramsEntity = entityDataSubCtx.getMsgEntity();
        WebSocketSessionRef sessionRef = entityDataSubCtx.getSessionRef();
        WebSocketService wsService = entityDataSubCtx.getWsService();
        try {
            Object[] params = paramsEntity.getParams();
            String methodName = paramsEntity.getMethodName();
            T result = websocketServiceScanning.invokeMethod(methodName, params);
            ResultEntity<T> resultEntity = paramsEntity.buildResult(result);
            wsService.sendWsMsg(sessionRef, paramsEntity.getMethodId(), resultEntity);
        } catch (Exception e) {
            log.info("execute error",e);
            wsService.sendWsMsg(sessionRef, paramsEntity.getMethodId(), paramsEntity.buildResult(e.getMessage(), false));
        }
    }

    private  <T> T getDefaultResult( Object[] params){
        if(params != null && params.length> 0){
            return (T)params[0];
        }
        return (T) (new Date().getTime()+"");
    }


    public <T> void pesponsePing(EntityDataSubCtx entityDataSubCtx) {
        ParamsEntity paramsEntity = entityDataSubCtx.getMsgEntity();
        WebSocketSessionRef sessionRef = entityDataSubCtx.getSessionRef();
        WebSocketService wsService = entityDataSubCtx.getWsService();
        try {
            Object[] params = paramsEntity.getParams();
            T result =  getDefaultResult(params);
            ResultEntity<T> resultEntity = paramsEntity.buildResult(result);
            resultEntity.setType("pong");
            wsService.sendWsMsg(sessionRef, paramsEntity.getMethodId(), resultEntity);
        } catch (Exception e) {
            log.info("execute error",e);
            wsService.sendWsMsg(sessionRef, paramsEntity.getMethodId(), paramsEntity.buildResult(e.getMessage(), false));
        }
    }

}
