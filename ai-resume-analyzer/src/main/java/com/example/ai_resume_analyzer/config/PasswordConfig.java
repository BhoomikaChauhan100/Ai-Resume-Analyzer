package com.example.ai_resume_analyzer.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 * BCrypt is used to securely hash user passwords.
 */
@Configuration
public class PasswordConfig {

	
	 /**
     * Creates a PasswordEncoder bean.
     * This bean will be used while registering and authenticating users.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
