package com.chess.card.api.ws.service.impl;

import com.chess.card.api.bean.room.RoomInfoVo;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.factory.ChessThreadFactory;
import com.chess.card.api.utils.JacksonUtil;
import com.chess.card.api.ws.DefaultWsContextService;
import com.chess.card.api.ws.api.DefaultDataSchedulingService;
import com.chess.card.api.ws.data.RoomUserData;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultWebSocketService implements WebSocketService {

    private final ConcurrentMap<String, WsSessionMetaData> wsSessionsMap = new ConcurrentHashMap<>();

    /**
     * 房间用户
     */
    private final ConcurrentMap<String, RoomUserData> roomUserListMap = new ConcurrentHashMap<>();


    @Autowired
    private DefaultWsContextService defaultWsContextService;

    private ExecutorService executor;
    private ScheduledExecutorService scheduler;

    private ScheduledExecutorService pingExecutor;

    @Autowired
    @Lazy
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
        String roomId = sessionRef.getRoomId();
        if (StringUtils.isNotBlank(roomId)) {
            RoomUserData roomUserData = roomUserListMap.get(roomId);
            if (roomUserData == null) {
                roomUserData = new RoomUserData();
                roomUserListMap.put(roomId, roomUserData);
            }

            switch (event.getEventType()) {
                case ESTABLISHED:
                    roomUserData.addUser(sessionRef);
                    break;
                case ERROR:
                    log.debug("[{}] Unknown websocket session error: {}. ", sessionId, event.getError().orElse(null));
                    break;
                case CLOSED:
                    roomUserData.removeUser(sessionRef);
                    //全部为空时删除掉
                    if (roomUserData.isEmpty()) {
                        roomUserListMap.remove(roomUserData);
                    }
                    break;
            }
        }

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

    public <T> void sendWsMsg(WebSocketSessionRef sessionRef, String cmdId, ResultEntity<T> update) {
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
        if ("ping".equals(type)) {
            defaultDataSchedulingService.pesponsePing(entityDataSubCtx);
        } else if ("callMethod".equals(type)) {
            log.info("handleWebSocketMsg msgEntity={}", msgEntity.toString());

            defaultDataSchedulingService.execute(entityDataSubCtx);
        }
    }

    @Override
    public void close(String sessionId, CloseStatus status) {

    }

    @Override
    public String getUserRoomId(String userId) {
        return defaultWsContextService.getUserRoomId(userId);
    }

    @Override
    public String getUserRoomIdBySessionId(String sessionId) {
        WsSessionMetaData wsSessionMetaData = wsSessionsMap.get(sessionId);
        String userId = wsSessionMetaData.getSessionRef().getSecurityCtx().getId();
        return defaultWsContextService.getUserRoomId(userId);
    }

    public void addLookerUser(String roomId,String sessionId){
        WsSessionMetaData wsSessionMetaData = this.wsSessionsMap.get(sessionId);
        if(wsSessionMetaData == null){
            return;
        }
        WebSocketSessionRef sessionRef = wsSessionMetaData.getSessionRef();
        RoomUserData roomUserData = roomUserListMap.get(roomId);
        if(roomUserData==null){
            roomUserData = new RoomUserData();
            roomUserListMap.put(roomId,roomUserData);
        }
        roomUserData.addLookerUser(sessionRef);
    }


    public void addRoomUser(String roomId,String sessionId){
        WsSessionMetaData wsSessionMetaData = this.wsSessionsMap.get(sessionId);
        if(wsSessionMetaData == null){
            return;
        }
        WebSocketSessionRef sessionRef = wsSessionMetaData.getSessionRef();
        RoomUserData roomUserData = roomUserListMap.get(roomId);
        if(roomUserData==null){
            roomUserData = new RoomUserData();
            roomUserListMap.put(roomId,roomUserData);
        }
        roomUserData.addRoomUser(sessionRef);

    }

    @Override
    public String addRoomUserBySessionId(String sessionId) {
        WsSessionMetaData wsSessionMetaData = wsSessionsMap.get(sessionId);
        if(wsSessionMetaData == null){
            log.error("会话不存在 sessionId={}",sessionId);
            throw new BuziException("会话不存在");
        }
        WebSocketSessionRef sessionRef = wsSessionMetaData.getSessionRef();
        String roomId = defaultWsContextService.getUserRoomId(sessionRef.getSecurityCtx().getId());
        RoomUserData roomUserData = roomUserListMap.get(roomId);
        if(roomUserData==null){
            roomUserData = new RoomUserData();
            roomUserListMap.put(roomId,roomUserData);
        }
        roomUserData.addRoomUser(sessionRef);

        return roomId;
    }


    public void notifyLookerUsers(String roomId, RoomInfoVo roomInfoVo){
        RoomUserData roomUserData = roomUserListMap.get(roomId);
        if(roomUserData == null){
            return;
        }

        List<WebSocketSessionRef> lookerUser = roomUserData.getLookerUser();
        if(CollectionUtils.isEmpty(lookerUser)){
            return;
        }

        for(WebSocketSessionRef sessionRef :lookerUser){
            ResultEntity<RoomInfoVo> resultEntity = new ResultEntity<>(roomInfoVo,"event","roomData");
            this.sendWsMsg(sessionRef, UUID.randomUUID().toString(), resultEntity);
        }

    }


    public void notifyRoomUsers(String roomId, RoomInfoVo roomInfoVo){
        RoomUserData roomUserData = roomUserListMap.get(roomId);
        if(roomUserData == null){
            return;
        }

        List<WebSocketSessionRef> roomUsers = roomUserData.getRoomUser();
        if(CollectionUtils.isEmpty(roomUsers)){
            return;
        }

        for(WebSocketSessionRef sessionRef :roomUsers){
            ResultEntity<RoomInfoVo> resultEntity = new ResultEntity<>(roomInfoVo,"event","roomData");
            this.sendWsMsg(sessionRef, UUID.randomUUID().toString(), resultEntity);
        }

    }

}
