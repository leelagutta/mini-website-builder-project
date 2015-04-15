package com.dhanya.mini.websitebuilder.exception;
/**
 * Oauth related exceptions.
 * 
 * @author Leela 
 */
public class OauthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OauthException(String message) {
		super(message);
	}
	
	public OauthException(String message, Exception ex) {
		super(message, ex);
	}
}