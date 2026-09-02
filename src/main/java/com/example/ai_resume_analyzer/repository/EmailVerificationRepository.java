package com.example.ai_resume_analyzer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ai_resume_analyzer.entity.EmailVerification;

public interface EmailVerificationRepository
        extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByEmail(String email);
}