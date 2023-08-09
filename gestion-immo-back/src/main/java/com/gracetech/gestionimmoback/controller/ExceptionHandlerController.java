package com.gracetech.gestionimmoback.controller;

import com.gracetech.gestionimmoback.dto.ApiError;
import com.gracetech.gestionimmoback.exception.CustomFunctionalException;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.exception.GenericException;
import com.gracetech.gestionimmoback.exception.UserNotActivatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author Robbile Controller to handler exception
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ElementNotFoundException.class)
	public ResponseEntity<Object> elementNotFound(ElementNotFoundException e) {
		log.debug("", e);
		return buildResponseEntity(new ApiError(e.getMessage(), HttpStatus.NOT_FOUND));
	}
	
	@ExceptionHandler(value = GenericException.class)
	public ResponseEntity<Object> genericException(GenericException e) {
		log.debug("", e);
		return buildResponseEntity(new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(value = UserNotActivatedException.class)
	public ResponseEntity<Object> userNotActivatedException(UserNotActivatedException e) {
		log.debug("", e);
		return buildResponseEntity(new ApiError(e.getMessage(), HttpStatus.FORBIDDEN));
	}
	
    @ExceptionHandler(value = AccessDeniedException.class )
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
    	log.debug("", ex);
		return buildResponseEntity(new ApiError(ex.getMessage(), HttpStatus.FORBIDDEN));
    }
	
	@ExceptionHandler(value = CustomFunctionalException.class)
	public ResponseEntity<Object> customFunctionalException(CustomFunctionalException e, WebRequest request) {
		log.debug("", e);
		return buildResponseEntity(new ApiError(e.getMessage(), e.getHttpStatus()));
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<Object> badCredentialException(BadCredentialsException e, WebRequest request) {
		log.debug("", e);
		return buildResponseEntity(new ApiError(e.getMessage(), HttpStatus.UNAUTHORIZED));
	}
	
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(Exception e, WebRequest request) {
        log.debug("No Handler Found Exception", e);
        return buildResponseEntity(new ApiError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }


	private ResponseEntity<Object> buildResponseEntity(ApiError err) {
		return new ResponseEntity<>(err, err.getStatus());
	}
}
