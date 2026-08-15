package com.echotech.auth.exception;

public class UserDoesNotExistException extends RuntimeException {

	public UserDoesNotExistException(String message) {
		super(message);
	}
}
