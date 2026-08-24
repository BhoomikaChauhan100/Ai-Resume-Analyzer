package com.example.ai_resume_analyzer.exception;


/**
 * Thrown when the user enters an invalid email or password.
 */
public class InvalidCredentialsException extends RuntimeException{
	
	  public InvalidCredentialsException(String message) {
	        super(message);
	    }

}
