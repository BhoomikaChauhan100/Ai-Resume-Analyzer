package com.example.ai_resume_analyzer.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ai_resume_analyzer.security.CustomUserDetailsService;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtService = jwtService;
        this.customUserDetailsService =
                customUserDetailsService;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {


        // =====================================
        // 1. GET JWT FROM COOKIE
        // =====================================

        String jwt = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if ("JWT".equals(cookie.getName())) {

                    jwt = cookie.getValue();

                    break;
                }
            }
        }


        // =====================================
        // 2. IF NO JWT
        // =====================================

        if (jwt == null || jwt.isBlank()) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }


        try {

            // =====================================
            // 3. EXTRACT EMAIL FROM JWT
            // =====================================

            String email =
                    jwtService.extractUsername(jwt);


            // =====================================
            // 4. CHECK NOT ALREADY AUTHENTICATED
            // =====================================

            if (email != null &&
                    SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {


                // =====================================
                // 5. LOAD USER
                // =====================================

                UserDetails userDetails =
                        customUserDetailsService
                        .loadUserByUsername(email);


                // =====================================
                // 6. VALIDATE JWT
                // =====================================

                if (jwtService.isTokenValid(
                        jwt,
                        userDetails)) {


                    // =====================================
                    // 7. CREATE AUTHENTICATION
                    // =====================================

                    UsernamePasswordAuthenticationToken authentication =
                            UsernamePasswordAuthenticationToken.authenticated(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );


                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                            .buildDetails(request)
                    );


                    // =====================================
                    // 8. SET SECURITY CONTEXT
                    // =====================================

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );


                    System.out.println(
                            "JWT Authentication Successful for: "
                            + email
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT Authentication Failed: "
                    + e.getMessage()
            );
        }


        // =====================================
        // 9. CONTINUE REQUEST
        // =====================================

        filterChain.doFilter(
                request,
                response
        );
    }
}