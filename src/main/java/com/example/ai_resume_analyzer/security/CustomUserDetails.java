package com.example.ai_resume_analyzer.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.ai_resume_analyzer.entity.User;

/**
 * Custom implementation of Spring Security's UserDetails.
 * It converts our User entity into a format understood by Spring Security.
 */
public class CustomUserDetails implements UserDetails{
     
	
	 private final User user;

	    public CustomUserDetails(User user) {
	        this.user = user;
	    }

	    /**
	     * Returns user role.
	     */
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {

	        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
	    }

	    /**
	     * Returns encrypted password.
	     */
	    @Override
	    public String getPassword() {
	        return user.getPassword();
	    }

	    /**
	     * Returns username (email).
	     */
	    @Override
	    public String getUsername() {
	        return user.getEmail();
	    }

	    /**
	     * Account is not expired.
	     */
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    /**
	     * Account is not locked.
	     */
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    /**
	     * Credentials are valid.
	     */
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    /**
	     * Account enabled status.
	     */
	    @Override
	    public boolean isEnabled() {
	        return user.isEnabled();
	    }

	    /**
	     * Returns original User entity.
	     */
	    public User getUser() {
	        return user;
}
}
