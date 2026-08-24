package com.example.ai_resume_analyzer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO used for forgot password request.
 */
@Getter
@Setter
@NoArgsConstructor
public class ForgotPasswordRequest {
	
	@NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

}
