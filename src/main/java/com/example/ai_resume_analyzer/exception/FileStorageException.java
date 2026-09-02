package com.example.ai_resume_analyzer.exception;


/**
 * Thrown when a file cannot be uploaded or stored.
 */
public class FileStorageException extends RuntimeException {
	

    public FileStorageException(String message) {
        super(message);
    }

}
