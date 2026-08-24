package com.example.ai_resume_analyzer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ai_resume_analyzer.service.BrevoService;

@RestController
public class EmailController {

    private final BrevoService brevoService;

    public EmailController(BrevoService brevoService) {
        this.brevoService = brevoService;
    }

    @GetMapping("/email/test")
    public ResponseEntity<String> sendTestEmail(@RequestParam String to) {

        brevoService.sendEmail(
                to,
                "Welcome to AI Resume Analyzer",
                """
                <h2>Hello!</h2>
                <p>This is a test email sent using the Brevo API.</p>
                <p>If you received this email, your Brevo integration is working successfully.</p>
                <br>
                <p>Thank you!</p>
                """
        );

        return ResponseEntity.ok("Email Sent Successfully!");
    }
}