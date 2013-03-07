package com.readysteady.user;

public class InvalidUserArgumentsException extends Exception{
	
	private static final long serialVersionUID = -3152376896007461700L;
	
	public InvalidUserArgumentsException(String message){
		super(message);
	}

}
