package com.chess.card.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BuziException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BuziException(String message){
        super(message);
    }

    public BuziException(Throwable cause)
    {
        super(cause);
    }

    public BuziException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
