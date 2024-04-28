package com.chess.card.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthorityException(String message){
        super(message);
    }

    public AuthorityException(Throwable cause)
    {
        super(cause);
    }

    public AuthorityException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
