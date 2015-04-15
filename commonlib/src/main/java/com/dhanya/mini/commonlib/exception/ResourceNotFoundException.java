package com.dhanya.mini.commonlib.exception;

/**
 * Resource not foundation exception, typiclaly used for 404 HTTP statuses.
 * 
 * @author Leela
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public ResourceNotFoundException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}