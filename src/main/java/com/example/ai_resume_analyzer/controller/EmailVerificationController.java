package com.example.ai_resume_analyzer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ai_resume_analyzer.service.EmailVerificationService;

@RestController
@RequestMapping("/auth")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        emailVerificationService.verifyOtp(email, otp);

        return ResponseEntity.ok("Email Verified Successfully");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(
            @RequestParam String email) {

        emailVerificationService.resendOtp(email);

        return ResponseEntity.ok("OTP Sent Successfully");
    }
}