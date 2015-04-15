package com.dhanya.mini.commonlib.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dhanya.mini.commonlib.exception.ResourceNotFoundException;
import com.dhanya.mini.commonlib.exception.ResultResponse;
/**
 * Controller for handling exceptions and errors.
 * 
 * @author Leela
 */
@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ResultResponse internalError(
			RuntimeException ex) {
		return new ResultResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ResultResponse notFoundException(
			RuntimeException ex) {
		return new ResultResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}
}