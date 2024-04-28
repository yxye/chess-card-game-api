package com.chess.card.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConfigException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConfigException(String message){
        super(message);
    }

    public ConfigException(Throwable cause)
    {
        super(cause);
    }

    public ConfigException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
