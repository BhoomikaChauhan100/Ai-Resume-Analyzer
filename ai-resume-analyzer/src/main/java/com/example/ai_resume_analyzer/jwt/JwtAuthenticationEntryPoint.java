package com.example.ai_resume_analyzer.jwt;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Handles unauthorized requests.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	 @Override
	    public void commence(HttpServletRequest request,
	                         HttpServletResponse response,
	                         AuthenticationException authException)
	            throws IOException, ServletException {

	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");

	        response.getWriter().write("""
	                {
	                  "status":401,
	                  "error":"Unauthorized",
	                  "message":"Invalid or Missing JWT Token"
	                }
	                """);
	    }

}
