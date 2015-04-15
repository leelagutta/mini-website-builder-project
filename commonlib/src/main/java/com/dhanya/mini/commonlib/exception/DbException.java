package com.dhanya.mini.commonlib.exception;

import java.sql.SQLException;

/**
 * Exception for database related errors.
 * 
 * @author Leela
 */
public class DbException extends RuntimeException {

	private static final long serialVersionUID = 1L; 
	
	private String message;
	
	private SQLException exception;
	
	public DbException(String message,SQLException exception){
		this.message = message;
		printMessage();
	}
	
	public void printMessage(){
		System.out.println(message+" "+exception);
	}
}