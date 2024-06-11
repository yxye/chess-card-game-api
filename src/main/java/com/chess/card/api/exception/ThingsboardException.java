
package com.chess.card.api.exception;

public class ThingsboardException extends Exception {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public ThingsboardException() {
        super();
    }

    public ThingsboardException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ThingsboardException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ThingsboardException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ThingsboardException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
