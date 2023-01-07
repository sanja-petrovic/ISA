package com.example.isa.exception;

public class ReachedPenaltyLimitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReachedPenaltyLimitException() {
        super("You can't schedule an appointment because you've reached your monthly penalty limit.");
    }
}