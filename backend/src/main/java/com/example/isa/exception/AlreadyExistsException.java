package com.example.isa.exception;

public class AlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyExistsException() {
        super(String.format("This entity already exists."));
    }
}
