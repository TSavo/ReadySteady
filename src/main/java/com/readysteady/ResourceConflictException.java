package com.readysteady;



public class ResourceConflictException extends RuntimeException{
	private static final long serialVersionUID = -8727160180566864383L;

	public ResourceConflictException(final String aMessage) {
		super(aMessage);
	}
}