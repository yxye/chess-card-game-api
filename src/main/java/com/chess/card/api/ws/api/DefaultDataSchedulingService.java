package com.chess.card.api.ws.api;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.ws.DefaultWsContextService;
import com.chess.card.api.ws.msg.EntityDataSubCtx;
import com.chess.card.api.ws.msg.ParamsEntity;
import com.chess.card.api.ws.msg.ResultEntity;
import com.chess.card.api.ws.processor.WebsocketServiceScanning;
import com.chess.card.api.ws.WebSocketSessionRef;
import com.chess.card.api.ws.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Date;


@Slf4j
@Service
public class DefaultDataSchedulingService {

    @Autowired
    private WebsocketServiceScanning websocketServiceScanning;

    @Autowired
    private DefaultWsContextService defaultWsContextService;


    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public <T> void execute(EntityDataSubCtx entityDataSubCtx) {


    }

    public <T> void execute2(EntityDataSubCtx entityDataSubCtx) {
        ParamsEntity paramsEntity = entityDataSubCtx.getMsgEntity();
        WebSocketSessionRef sessionRef = entityDataSubCtx.getSessionRef();
        WebSocketService wsService = entityDataSubCtx.getWsService();
        SecurityUser securityCtx = entityDataSubCtx.getSessionRef().getSecurityCtx();
        defaultWsContextService.setEntityDataSubCtx(entityDataSubCtx);
        try {
            Object[] params = paramsEntity.getParams();
            String methodName = paramsEntity.getMethodName();
            T result = websocketServiceScanning.invokeMethod(methodName, params);
            ResultEntity<T> resultEntity = paramsEntity.buildResult(result);
            wsService.sendWsMsg(sessionRef, paramsEntity.getMethodId(), resultEntity);
        } catch (Exception e) {
            String erroeMsg = getErrorMessage(e);
            log.info("execute error",e);
            wsService.sendWsMsg(sessionRef, paramsEntity.getMethodId(), paramsEntity.buildResult(erroeMsg, false));
        }
    }

    public String  getErrorMessage(Exception e) {
        try {
            Throwable o = e;
            for (int i = 0; i < 3; i++) {
                if (o != null && o.getClass().getName().contains("com.chess.card")) {
                    return o.getMessage();
                }
                o = o.getCause();
            }

            return e.getMessage();
        }catch (Exception ex){
            return "操作失败";
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
