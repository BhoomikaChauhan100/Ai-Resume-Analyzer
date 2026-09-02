package com.example.ai_resume_analyzer.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO used for resetting password.
 */
@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordRequest {
	

    @NotBlank(message = "Reset token is required")
    private String token;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String newPassword;


}
