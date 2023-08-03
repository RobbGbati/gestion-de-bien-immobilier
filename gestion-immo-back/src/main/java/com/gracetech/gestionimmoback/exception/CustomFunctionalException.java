package com.gracetech.gestionimmoback.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

import lombok.Getter;


/**
 * 
 * @author Robbile
 * This class is used to throw functional exception in the application
 */
@Getter
public class CustomFunctionalException extends NestedRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;
	private final String message;
	
	public CustomFunctionalException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.message = message;
	}

}
