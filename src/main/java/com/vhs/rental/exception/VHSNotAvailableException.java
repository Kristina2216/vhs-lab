package com.vhs.rental.exception;

public class VHSNotAvailableException extends RuntimeException{
    public VHSNotAvailableException(String message) {
        super(message);
    }

    public VHSNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public VHSNotAvailableException(Throwable cause) {
        VHSErrorResponse error = new VHSErrorResponse();

    }
}
