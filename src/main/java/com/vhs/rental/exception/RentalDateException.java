package com.vhs.rental.exception;

public class RentalDateException extends RuntimeException{
    public RentalDateException(String message) {
        super(message);
    }

    public RentalDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalDateException(Throwable cause) {
        super(cause);
    }
}
