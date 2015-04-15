package com.dhanya.mini.commonlib.exception;

/**
 * Json exception class
 * 
 * @author Leela
 */
public class JsonException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public JsonException(String errorMessage){
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}