package com.example.isa.exception;

public class PassedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PassedException() {
        super("This appointment is in the past.");
    }
}
