package com.example.isa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NoCompletedQuestionnaire extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoCompletedQuestionnaire() {
        super("Donor must have a filled out questionnaire to complete this action.");
    }
}

