package com.chess.card.api.ws;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.chess.card.api.exception.UnLoginException;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.ws.msg.EntityDataSubCtx;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class DefaultWsContextService {
    private final ThreadLocal<EntityDataSubCtx> securityCtxLocal = new TransmittableThreadLocal<>();

    /**
     * 用户与房间进行绑定
     */
    private final ConcurrentMap<String, String> userRoomIdMapping = new ConcurrentHashMap<>();

    /**
     * 消息总线
     */
    private EventBus eventBus = new EventBus();

    public void setEntityDataSubCtx(EntityDataSubCtx entityDataSubCtx) {
        securityCtxLocal.set(entityDataSubCtx);
    }

    public EntityDataSubCtx getEntityDataSubCtx() {
        EntityDataSubCtx entityDataSubCtx = securityCtxLocal.get();

        if (entityDataSubCtx == null) {
            log.info("用户未登录 securityUser=null");
            throw new UnLoginException("用户未登录");
        }
        return entityDataSubCtx;
    }

    public SecurityUser getSecurityUser() {
        EntityDataSubCtx entityDataSubCtx = this.getEntityDataSubCtx();

        SecurityUser securityCtx = entityDataSubCtx.getSessionRef().getSecurityCtx();

        if (securityCtx == null) {
            log.info("用户未登录 securityUser=null");
            throw new UnLoginException("用户未登录");
        }
        return securityCtx;
    }


    public void enterGameRoom(String userId, String roomId) {
        userRoomIdMapping.put(userId, roomId);
    }

    public String getUserRoomId(String userId) {
        if (!this.userRoomIdMapping.containsKey(userId)) {
            log.info("用户未在房间内 userRoomIdMapping not has {}", userId);
            throw new UnLoginException("用户未在房间内");
        }
        return this.userRoomIdMapping.get(userId);
    }

    public void registerEvent(Consumer<String> function) {
        eventBus.register(new Object() {
            @Subscribe
            public void handleEvent(String event) {
                function.accept(event);
            }
        });
    }
}
