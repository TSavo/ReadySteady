package com.readysteady.user;

public class NoSuchUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3152376896007460700L;

	public NoSuchUserException(){}
	public NoSuchUserException(String message){
		super(message);
	}
}
