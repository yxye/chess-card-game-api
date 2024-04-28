package com.chess.card.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LuckyDrawException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LuckyDrawException(String message){
        super(message);
    }

    public LuckyDrawException(Throwable cause)
    {
        super(cause);
    }

    public LuckyDrawException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
