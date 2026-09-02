package com.example.ai_resume_analyzer.exception;



/**
 * Thrown when a user tries to register with an email
 * that already exists in the database.
 */
public class UserAlreadyExistsException  extends RuntimeException {
	
	public UserAlreadyExistsException(String message) {
        super(message);
    }


}
