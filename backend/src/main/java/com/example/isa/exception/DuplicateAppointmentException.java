package com.example.isa.exception;

public class DuplicateAppointmentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DuplicateAppointmentException() {
        super("Duplicate appointment scheduling.");
    }

}
