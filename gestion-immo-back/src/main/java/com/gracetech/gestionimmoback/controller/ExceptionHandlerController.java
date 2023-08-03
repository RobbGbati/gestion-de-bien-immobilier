package com.gracetech.gestionimmoback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gracetech.gestionimmoback.exception.CustomFunctionalException;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.exception.GenericException;
import com.gracetech.gestionimmoback.exception.UserNotActivatedException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Robbile Controller to handler exception
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ElementNotFoundException.class)
	public ResponseEntity<String> elementNotFound(ElementNotFoundException e) {
		log.debug("", e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = GenericException.class)
	public ResponseEntity<String> genericException(GenericException e) {
		log.debug("", e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UserNotActivatedException.class)
	public ResponseEntity<String> userNotActivatedException(UserNotActivatedException e) {
		log.debug("", e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
	}
	
    @ExceptionHandler(value = AccessDeniedException.class )
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
    	log.debug("", ex);
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(value = CustomFunctionalException.class)
	public ResponseEntity<String> customFunctionalException(CustomFunctionalException e, WebRequest request) {
		log.debug("", e);
		return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
	}
	
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleNoHandlerFoundException(Exception e, WebRequest request) {
        log.debug("No Handler Found Exception", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
