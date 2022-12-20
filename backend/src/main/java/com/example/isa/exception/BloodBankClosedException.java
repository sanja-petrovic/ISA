package com.example.isa.exception;

public class BloodBankClosedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public BloodBankClosedException() {
	        super("Blood bank is closed at selected time interval.");
	    }
}
