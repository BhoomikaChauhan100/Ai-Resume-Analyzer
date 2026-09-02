package com.example.ai_resume_analyzer.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO used to receive login credentials.
 */
@NoArgsConstructor
public class LoginRequest {
	
	
	 @NotBlank(message = "Email is required")
	    @Email(message = "Please enter a valid email")
	    private String email;

	    @NotBlank(message = "Password is required")
	    private String password;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	    
	    

}
