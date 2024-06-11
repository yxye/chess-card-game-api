package com.chess.card.api.ws.service.impl;

import com.chess.card.api.factory.ChessThreadFactory;
import com.chess.card.api.utils.JacksonUtil;
import com.chess.card.api.ws.api.DefaultDataSchedulingService;
import com.chess.card.api.ws.msg.EntityDataSubCtx;
import com.chess.card.api.ws.msg.ParamsEntity;
import com.chess.card.api.ws.msg.ResultEntity;
import com.chess.card.api.ws.service.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.chess.card.api.factory.ChessExecutors;
import com.chess.card.api.ws.WebSocketMsgEndpoint;
import com.chess.card.api.ws.WebSocketSessionRef;
import com.chess.card.api.ws.data.WsSessionMetaData;

import com.chess.card.api.ws.event.SessionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultWebSocketService implements WebSocketService {

    private final ConcurrentMap<String, WsSessionMetaData> wsSessionsMap = new ConcurrentHashMap<>();

    private ExecutorService executor;
    private ScheduledExecutorService scheduler;

    private ScheduledExecutorService pingExecutor;

    @Autowired
    private DefaultDataSchedulingService defaultDataSchedulingService;

    private final WebSocketMsgEndpoint msgEndpoint;

    private long pingTimeout = 30000;

    public static final int NUMBER_OF_PING_ATTEMPTS = 3;

    @PostConstruct
    public void initExecutor() {
        executor = ChessExecutors.newWorkStealingPool(50, getClass());

        ThreadFactory tbThreadFactory = ChessThreadFactory.forName("ws-entity-sub-scheduler");

        scheduler = Executors.newScheduledThreadPool(1, tbThreadFactory);

        pingExecutor = Executors.newSingleThreadScheduledExecutor(ChessThreadFactory.forName("check-web-socket-ping"));

        pingExecutor.scheduleWithFixedDelay(this::sendPing, pingTimeout / NUMBER_OF_PING_ATTEMPTS, pingTimeout / NUMBER_OF_PING_ATTEMPTS, TimeUnit.MILLISECONDS);

    }

    @Override
    public void handleWebSocketSessionEvent(WebSocketSessionRef sessionRef, SessionEvent event) {
        String sessionId = sessionRef.getSessionId();
        switch (event.getEventType()) {
            case ESTABLISHED:
                wsSessionsMap.put(sessionId, new WsSessionMetaData(sessionRef));
                break;
            case ERROR:
                log.debug("[{}] Unknown websocket session error: {}. ", sessionId, event.getError().orElse(null));
                break;
            case CLOSED:
                wsSessionsMap.remove(sessionId);
                break;
        }
    }

    public <T> void sendWsMsg(String sessionId, String cmdId, ResultEntity<T> update) {
        WsSessionMetaData md = wsSessionsMap.get(sessionId);
        if (md != null) {
            sendWsMsg(md.getSessionRef(), cmdId, update);
        }
    }

    public  <T> void sendWsMsg(WebSocketSessionRef sessionRef, String cmdId, ResultEntity<T> update) {
        try {
            String msg = JacksonUtil.OBJECT_MAPPER.writeValueAsString(update);
            executor.submit(() -> {
                try {
                    msgEndpoint.send(sessionRef, cmdId, msg);
                } catch (IOException e) {
                    log.warn("[{}] Failed to send reply: {}", sessionRef.getSessionId(), update, e);
                }
            });
        } catch (JsonProcessingException e) {
            log.warn("[{}] Failed to encode reply: {}", sessionRef.getSessionId(), update, e);
        }
    }

    private void sendPing() {
        long currentTime = System.currentTimeMillis();
        wsSessionsMap.values().forEach(md ->
                executor.submit(() -> {
                    try {
                        msgEndpoint.sendPing(md.getSessionRef(), currentTime);
                    } catch (IOException e) {
                        log.warn("[{}] Failed to send ping: {}", md.getSessionRef().getSessionId(), e);
                    }
                }));
    }

    @Override
    public void
    handleWebSocketMsg(WebSocketSessionRef sessionRef, String msg) {
        ParamsEntity msgEntity = JacksonUtil.fromString(msg, ParamsEntity.class);
        String type = msgEntity.getType();
        EntityDataSubCtx entityDataSubCtx = new EntityDataSubCtx(this, msgEntity, sessionRef);
        if("ping".equals(type)){
            defaultDataSchedulingService.pesponsePing(entityDataSubCtx);
        }else if("callMethod".equals(type)){
            log.info("handleWebSocketMsg msgEntity={}",msgEntity.toString());

            defaultDataSchedulingService.execute(entityDataSubCtx);
        }
    }

    @Override
    public void close(String sessionId, CloseStatus status) {

    }


}
