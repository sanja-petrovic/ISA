package com.example.isa.exception;

public class NewAppointmentTooSoonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NewAppointmentTooSoonException() {
        super("Six months must pass between donations.");
    }
}