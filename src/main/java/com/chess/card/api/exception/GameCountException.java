package com.chess.card.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameCountException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GameCountException(String message){
        super(message);
    }

    public GameCountException(Throwable cause)
    {
        super(cause);
    }

    public GameCountException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
