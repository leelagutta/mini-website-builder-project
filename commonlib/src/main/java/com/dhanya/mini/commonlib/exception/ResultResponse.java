package com.dhanya.mini.commonlib.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Error response object for JSON result purposes.
 * 
 * @author Leela
 */
public class ResultResponse {

	private HttpStatus status;

	private String message;

	private List<InputFieldError> inputFieldErrors;

	public ResultResponse(HttpStatus status,
			List<InputFieldError> inputFieldErrors) {
		this.status = status;
		this.inputFieldErrors = inputFieldErrors;
	}

	public ResultResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<InputFieldError> getInputFieldErrors() {
		return inputFieldErrors;
	}

	public void setInputFieldErrors(List<InputFieldError> inputFieldErrors) {
		this.inputFieldErrors = inputFieldErrors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}