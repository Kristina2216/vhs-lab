package com.vhs.rental.exception;

public class DeletedRentalException extends RuntimeException{
    public DeletedRentalException(String message) {
        super(message);
    }

    public DeletedRentalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeletedRentalException(Throwable cause) {
        super(cause);
    }
}
