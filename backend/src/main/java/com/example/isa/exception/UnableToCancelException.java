package com.example.isa.exception;

public class UnableToCancelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnableToCancelException() {
        super("Cancellations can only be made up to 24 hours before the scheduled appointment time.");
    }
}
