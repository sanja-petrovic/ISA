package com.example.isa.exception;

public class CantScheduleTwiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CantScheduleTwiceException() {
        super(String.format("A donor cannot schedule the same appointment twice."));
    }
}
