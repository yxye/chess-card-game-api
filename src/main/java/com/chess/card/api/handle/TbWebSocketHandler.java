
package com.chess.card.api.handle;

import com.chess.card.api.config.WebSocketConfiguration;
import com.chess.card.api.controller.plugin.TbWebSocketMsg;
import com.chess.card.api.controller.plugin.TbWebSocketMsgType;
import com.chess.card.api.controller.plugin.TbWebSocketPingMsg;
import com.chess.card.api.controller.plugin.TbWebSocketTextMsg;
import com.chess.card.api.ws.WebSocketMsgEndpoint;
import com.chess.card.api.ws.WebSocketSessionRef;
import com.chess.card.api.ws.WebSocketSessionType;
import com.chess.card.api.ws.service.WebSocketService;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.ws.event.SessionEvent;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.NativeWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
;import javax.websocket.RemoteEndpoint;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.security.InvalidParameterException;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
@Slf4j
public class TbWebSocketHandler extends TextWebSocketHandler implements WebSocketMsgEndpoint {

    private long sendTimeout = 5000;

    private long pingTimeout = 30000;

    private int wsMaxQueueMessagesPerSession = 1000;

    public static final int NUMBER_OF_PING_ATTEMPTS = 3;
    private final ConcurrentMap<String, SessionMetaData> internalSessionMap = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, String> externalSessionMap = new ConcurrentHashMap<>();


    @Autowired
    @Lazy
    private WebSocketService webSocketService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            log.info("WebSocketSession = {}", message);
            SessionMetaData sessionMd = internalSessionMap.get(session.getId());
            if (sessionMd != null) {
                webSocketService.handleWebSocketMsg(sessionMd.sessionRef, message.getPayload());
            } else {
                log.trace("[{}] Failed to find session", session.getId());
                session.close(CloseStatus.SERVER_ERROR.withReason("Session not found!"));
            }
        } catch (IOException e) {
            log.warn("IO error", e);
        }

    }

    /**
     * 响应ping请求
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        try {
            SessionMetaData sessionMd = internalSessionMap.get(session.getId());
            if (sessionMd != null) {
                log.trace("[{}] Processing pong response {}", sessionMd, session.getId(), message.getPayload());
                sessionMd.processPongMessage(System.currentTimeMillis());
            } else {
                log.trace("[{}] Failed to find session", session.getId());
                session.close(CloseStatus.SERVER_ERROR.withReason("Session not found!"));
            }
        } catch (IOException e) {
            log.warn("IO error", e);
        }
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished");
        super.afterConnectionEstablished(session);
        try {
            if (session instanceof NativeWebSocketSession) {
                Session nativeSession = ((NativeWebSocketSession) session).getNativeSession(Session.class);
                if (nativeSession != null) {
                    nativeSession.getAsyncRemote().setSendTimeout(sendTimeout);
                }
            }
            String internalSessionId = session.getId();
            WebSocketSessionRef sessionRef = toRef(session);
            String externalSessionId = sessionRef.getSessionId();

            if (!checkLimits(session, sessionRef)) {
                return;
            }
            internalSessionMap.put(internalSessionId, new SessionMetaData(session, sessionRef, wsMaxQueueMessagesPerSession));
            externalSessionMap.put(externalSessionId, internalSessionId);
            processInWebSocketService(sessionRef, SessionEvent.onEstablished());
        } catch (InvalidParameterException e) {
            log.warn("[{}] Failed to start session", session.getId(), e);
            session.close(CloseStatus.BAD_DATA.withReason(e.getMessage()));
        } catch (Exception e) {
            log.warn("[{}] Failed to start session", session.getId(), e);
            session.close(CloseStatus.SERVER_ERROR.withReason(e.getMessage()));
        }
    }

    private boolean checkLimits(WebSocketSession session, WebSocketSessionRef sessionRef) throws Exception {

        return true;
    }


    private WebSocketSessionRef toRef(WebSocketSession session) throws IOException {
        URI sessionUri = session.getUri();
        String path = sessionUri.getPath();
        path = path.substring(WebSocketConfiguration.WS_PLUGIN_PREFIX.length());
        if (path.length() == 0) {
            throw new IllegalArgumentException("URL should contain plugin token!");
        }
        String[] pathElements = path.split("/");
        String serviceToken = pathElements[0];

        WebSocketSessionType sessionType = WebSocketSessionType.forName(serviceToken).orElseThrow(() -> new InvalidParameterException("Can't find plugin with specified token!"));
        //当前用户
        SecurityUser currentUser = (SecurityUser) ((Authentication) session.getPrincipal()).getPrincipal();
        return WebSocketSessionRef.builder()
                .sessionId(UUID.randomUUID().toString())
                .securityCtx(currentUser)
                .localAddress(session.getLocalAddress())
                .remoteAddress(session.getRemoteAddress())
                .sessionType(sessionType)
                .build();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
        SessionMetaData sessionMd = internalSessionMap.remove(session.getId());
        if (sessionMd != null) {
            externalSessionMap.remove(sessionMd.sessionRef.getSessionId());
            processInWebSocketService(sessionMd.sessionRef, SessionEvent.onClosed());
            log.info("[{}][{}][{}] Session is closed", sessionMd.sessionRef.getSecurityCtx().getAccount(), sessionMd.sessionRef.getSessionId(), session.getId());
        } else {
            log.info("[{}] Session is closed", session.getId());
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable tError) throws Exception {
        super.handleTransportError(session, tError);
        SessionMetaData sessionMd = internalSessionMap.get(session.getId());
        if (sessionMd != null) {
            processInWebSocketService(sessionMd.sessionRef, SessionEvent.onError(tError));
        } else {
            log.trace("[{}] Failed to find session", session.getId());
        }
        log.trace("[{}] Session transport error", session.getId(), tError);

    }


    @Override
    public void send(WebSocketSessionRef sessionRef, String subscriptionId, String msg) throws IOException {
        String externalId = sessionRef.getSessionId();
        log.debug("[{}] Processing {}", externalId, msg);
        String internalId = externalSessionMap.get(externalId);
        if (internalId != null) {
            SessionMetaData sessionMd = internalSessionMap.get(internalId);
            if (sessionMd != null) {
                sessionMd.sendMsg(msg);
            } else {
                log.warn("[{}][{}] Failed to find session by internal id", externalId, internalId);
            }
        } else {
            log.warn("[{}] Failed to find session by external id", externalId);
        }
    }


    private void processInWebSocketService(WebSocketSessionRef sessionRef, SessionEvent event) {
        try {
            webSocketService.handleWebSocketSessionEvent(sessionRef, event);
        } catch (BeanCreationNotAllowedException e) {
            log.warn("[{}] Failed to close session due to possible shutdown state", sessionRef.getSessionId());
        }
    }



    @Override
    public void sendPing(WebSocketSessionRef sessionRef, long currentTime) throws IOException {
        String externalId = sessionRef.getSessionId();
        String internalId = externalSessionMap.get(externalId);
        if (internalId != null) {
            SessionMetaData sessionMd = internalSessionMap.get(internalId);
            if (sessionMd != null) {
                sessionMd.sendPing(currentTime);
            } else {
                log.warn("[{}][{}] Failed to find session by internal id", externalId, internalId);
            }
        } else {
            log.warn("[{}] Failed to find session by external id", externalId);
        }
    }



    @Override
    public void close(WebSocketSessionRef sessionRef, CloseStatus withReason) throws IOException {

    }

    class SessionMetaData implements SendHandler {
        private final WebSocketSession session;
        private final RemoteEndpoint.Async asyncRemote;
        private final WebSocketSessionRef sessionRef;

        final AtomicBoolean isSending = new AtomicBoolean(false);
        private final Queue<TbWebSocketMsg<?>> msgQueue;

        private volatile long lastActivityTime;

        SessionMetaData(WebSocketSession session, WebSocketSessionRef sessionRef, int maxMsgQueuePerSession) {
            super();
            this.session = session;
            Session nativeSession = ((NativeWebSocketSession) session).getNativeSession(Session.class);
            this.asyncRemote = nativeSession.getAsyncRemote();
            this.sessionRef = sessionRef;
            this.msgQueue = new LinkedBlockingQueue<>(maxMsgQueuePerSession);
            this.lastActivityTime = System.currentTimeMillis();
        }

        void sendPing(long currentTime) {
            try {
                long timeSinceLastActivity = currentTime - lastActivityTime;
                if (timeSinceLastActivity >= pingTimeout) {
                    log.warn("[{}] Closing session due to ping timeout", session.getId());
                    closeSession(CloseStatus.SESSION_NOT_RELIABLE);
                } else if (timeSinceLastActivity >= pingTimeout / NUMBER_OF_PING_ATTEMPTS) {
                    sendMsg(TbWebSocketPingMsg.INSTANCE);
                }
            } catch (Exception e) {
                log.trace("[{}] Failed to send ping msg", session.getId(), e);
                closeSession(CloseStatus.SESSION_NOT_RELIABLE);
            }
        }

        void closeSession(CloseStatus reason) {
            try {
                close(this.sessionRef, reason);
            } catch (IOException ioe) {
                log.trace("[{}] Session transport error", session.getId(), ioe);
            } finally {
                msgQueue.clear();
            }
        }

        void processPongMessage(long currentTime) {
            lastActivityTime = currentTime;
        }

        void sendMsg(String msg) {
            sendMsg(new TbWebSocketTextMsg(msg));
        }

        void sendMsg(TbWebSocketMsg<?> msg) {
            try {
                msgQueue.add(msg);
            } catch (RuntimeException e) {
                if (log.isTraceEnabled()) {
                    log.trace("[{}][{}] Session closed due to queue error", session.getId(), e);
                } else {
                    log.info("[{}][{}] Session closed due to queue error", session.getId());
                }
                closeSession(CloseStatus.POLICY_VIOLATION.withReason("Max pending updates limit reached!"));
                return;
            }

            processNextMsg();
        }

        private void sendMsgInternal(TbWebSocketMsg<?> msg) {
            try {
                if (TbWebSocketMsgType.TEXT.equals(msg.getType())) {
                    TbWebSocketTextMsg textMsg = (TbWebSocketTextMsg) msg;
                    this.asyncRemote.sendText(textMsg.getMsg(), this);
                    // isSending status will be reset in the onResult method by call back
                } else {
                    TbWebSocketPingMsg pingMsg = (TbWebSocketPingMsg) msg;
                    this.asyncRemote.sendPing(pingMsg.getMsg()); // blocking call
                    isSending.set(false);
                    processNextMsg();
                }
            } catch (Exception e) {
                log.trace("[{}] Failed to send msg", session.getId(), e);
                closeSession(CloseStatus.SESSION_NOT_RELIABLE);
            }
        }

        @Override
        public void onResult(SendResult result) {
            if (!result.isOK()) {
                log.trace("[{}] Failed to send msg", session.getId(), result.getException());
                closeSession(CloseStatus.SESSION_NOT_RELIABLE);
                return;
            }

            isSending.set(false);
            processNextMsg();
        }

        private void processNextMsg() {
            if (msgQueue.isEmpty() || !isSending.compareAndSet(false, true)) {
                return;
            }
            TbWebSocketMsg<?> msg = msgQueue.poll();
            if (msg != null) {
                sendMsgInternal(msg);
            } else {
                isSending.set(false);
            }
        }
    }

}
