package com.chess.ws.api.ws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum WebSocketSessionType {
    DATA("data"),
    NOTIFICATIONS("notifications"),
    CHESS("chess");

    private final String name;

    public static Optional<WebSocketSessionType> forName(String name) {
        return Arrays.stream(values())
                .filter(sessionType -> sessionType.getName().equals(name))
                .findFirst();
    }

}
