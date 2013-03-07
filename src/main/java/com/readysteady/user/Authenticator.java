package com.readysteady.user;


public interface Authenticator {

	User authenticate(User aCredential) throws NoSuchUserException;

}
