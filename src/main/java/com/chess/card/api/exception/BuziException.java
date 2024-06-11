package com.chess.ws.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BuziException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    public BuziException(String message){
        super(message);
    }
    public BuziException(int code,String message){
        super(message);
        this.code = code;
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
