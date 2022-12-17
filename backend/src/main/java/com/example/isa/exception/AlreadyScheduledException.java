package com.example.isa.exception;

public class AlreadyScheduledException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyScheduledException() {
        super("This appointment has already been scheduled by a donor.");
    }
}