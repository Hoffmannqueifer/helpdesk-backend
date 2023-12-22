package com.hoffmann.helpdesk.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hoffmann.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.hoffmann.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandarError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){
		StandarError error  = new StandarError(System.currentTimeMillis(),HttpStatus.NOT_FOUND.value(), "Object Not Found",
				ex.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandarError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
		StandarError error  = new StandarError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Violação de dados",
				ex.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> validationErros(MethodArgumentNotValidException ex, HttpServletRequest request){
		ValidationError errors = new ValidationError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Erro na validação dos campos",
				ex.getMessage(),request.getRequestURI());
		
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addErrors(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
