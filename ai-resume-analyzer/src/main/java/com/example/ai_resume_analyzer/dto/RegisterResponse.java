package com.example.ai_resume_analyzer.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO returned after successful registration.
 */

@AllArgsConstructor
public class RegisterResponse {
	
	private String message;

    private String name;

    private String email;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
    


}
