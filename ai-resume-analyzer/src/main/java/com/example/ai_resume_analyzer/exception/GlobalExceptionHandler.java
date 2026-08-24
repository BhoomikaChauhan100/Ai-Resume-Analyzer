package com.example.ai_resume_analyzer.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions globally.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles validation errors.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {

		String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles duplicate email registration.
	 */
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	/**
	 * Handles user not found.
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles invalid login credentials.
	 */
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Handles file upload errors.
	 */
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<String> handleFileStorage(FileStorageException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles all other unexpected exceptions.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {

		return new ResponseEntity<>("Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
