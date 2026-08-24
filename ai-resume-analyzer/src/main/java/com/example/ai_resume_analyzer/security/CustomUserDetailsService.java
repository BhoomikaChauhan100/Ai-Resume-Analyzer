package com.example.ai_resume_analyzer.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ai_resume_analyzer.entity.User;
import com.example.ai_resume_analyzer.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	 private final UserRepository userRepository;

	    // Constructor Injection
	    public CustomUserDetailsService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }

	    /**
	     * Load user from database using email.
	     */
	    @Override
	    public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {

	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() ->
	                        new UsernameNotFoundException("User not found with email: " + email));

	        return new CustomUserDetails(user);

}
}
