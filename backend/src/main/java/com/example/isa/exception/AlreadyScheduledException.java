package com.example.isa.exception;

public class AlreadyScheduledException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyScheduledException() {
        super(String.format("This appointment has already been scheduled by a donor."));
    }
}