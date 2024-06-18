package com.chess.card.api.ws;

import com.chess.card.api.security.model.SecurityUser;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


@RequiredArgsConstructor
@Builder
@Getter
public class WebSocketSessionRef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private final String sessionId;

    /**
     *
     */
    private final String roomId;

    /**
     *
     */
    private final String seatId;

    private final SecurityUser securityCtx;
    private final InetSocketAddress localAddress;
    private final InetSocketAddress remoteAddress;
    private final WebSocketSessionType sessionType;

    private final AtomicInteger sessionSubIdSeq = new AtomicInteger();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebSocketSessionRef that = (WebSocketSessionRef) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    @Override
    public String toString() {
        return "WebSocketSessionRef{" +
                "sessionId='" + sessionId + '\'' +
                ", localAddress=" + localAddress +
                ", remoteAddress=" + remoteAddress +
                ", sessionType=" + sessionType +
                '}';
    }
}
