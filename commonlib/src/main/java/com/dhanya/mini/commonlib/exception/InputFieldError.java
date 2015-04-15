package com.dhanya.mini.commonlib.exception;

/**
 * Input field error
 *  
 *  @author Leela
 */
public class InputFieldError {

	private String field;
	
	private String message;

	public InputFieldError() {};

	public InputFieldError(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
