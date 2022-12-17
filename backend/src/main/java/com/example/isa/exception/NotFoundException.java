package com.example.isa.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("This entity could not be found.");
    }
}
