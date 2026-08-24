package com.example.ai_resume_analyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ai_resume_analyzer.jwt.JwtAuthenticationEntryPoint;
import com.example.ai_resume_analyzer.jwt.JwtAuthenticationFilter;
import com.example.ai_resume_analyzer.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordConfig passwordConfig;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;


    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailsService customUserDetailsService,
            PasswordConfig passwordConfig,
            JwtAuthenticationEntryPoint authenticationEntryPoint) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordConfig = passwordConfig;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    // =========================================
    // SECURITY FILTER CHAIN
    // =========================================

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

            // =====================================
            // CSRF
            // =====================================

            .csrf(csrf ->
                    csrf.disable()
            )


            // =====================================
            // AUTHORIZATION
            // =====================================

            .authorizeHttpRequests(auth -> auth

                // ---------- PUBLIC ----------

                .requestMatchers(
                        "/",
                        "/error",

                        "/register",
                        "/verify-otp",

                        "/auth/login",
                        "/auth/login/**",
                        "/auth/verify-otp",
                        "/auth/resend-otp",

                        "/email/**",

                        "/css/**",
                        "/js/**",
                        "/images/**"
                )
                .permitAll()


                // ---------- PROTECTED ----------

                .anyRequest()
                .authenticated()
            )


            // =====================================
            // STATELESS
            // =====================================

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )


            // =====================================
            // EXCEPTION HANDLING
            // =====================================

            .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(
                            authenticationEntryPoint
                    )
            )


            // =====================================
            // AUTH PROVIDER
            // =====================================

            .authenticationProvider(
                    authenticationProvider()
            )


            // =====================================
            // JWT FILTER
            // =====================================

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );


        return http.build();
    }


    // =========================================
    // AUTHENTICATION PROVIDER
    // =========================================

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        customUserDetailsService
                );

        provider.setPasswordEncoder(
                passwordConfig.passwordEncoder()
        );

        return provider;
    }


    // =========================================
    // AUTHENTICATION MANAGER
    // =========================================

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }
}