package com.example.isa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughSupplyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotEnoughSupplyException() {
        super("Not enough supply for this blood type in the bank!");
    }
}