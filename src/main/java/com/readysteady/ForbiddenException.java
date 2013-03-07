package com.readysteady;



public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = -2135768289298302425L;

	public ForbiddenException(final String aMessage) {
		super(aMessage);
	}
}
