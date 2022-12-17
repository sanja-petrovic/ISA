package com.example.isa.exception;

public class NoCompletedQuestionnaire extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoCompletedQuestionnaire() {
        super(String.format("Donor must have a filled out questionnaire to complete this action."));
    }
}

