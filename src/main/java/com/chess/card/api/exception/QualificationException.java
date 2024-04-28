package com.chess.card.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QualificationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QualificationException(String message){
        super(message);
    }

    public QualificationException(Throwable cause)
    {
        super(cause);
    }

    public QualificationException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
