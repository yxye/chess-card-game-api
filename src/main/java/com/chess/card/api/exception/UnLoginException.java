package com.chess.card.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnLoginException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnLoginException(String message){
        super(message);
    }

    public UnLoginException(Throwable cause)
    {
        super(cause);
    }

    public UnLoginException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
