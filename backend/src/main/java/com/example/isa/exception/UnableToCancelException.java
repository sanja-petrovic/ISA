package com.example.isa.exception;

public class UnableToCancelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnableToCancelException() {
        super("Appointment can only be cancelled up to 24 hours before.");
    }
}
