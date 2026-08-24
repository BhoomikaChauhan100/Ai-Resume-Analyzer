
package com.example.ai_resume_analyzer.serviceimpl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.ai_resume_analyzer.entity.EmailVerification;
import com.example.ai_resume_analyzer.entity.User;
import com.example.ai_resume_analyzer.repository.EmailVerificationRepository;
import com.example.ai_resume_analyzer.repository.UserRepository;
import com.example.ai_resume_analyzer.service.BrevoService;
import com.example.ai_resume_analyzer.service.EmailVerificationService;

@Service
public class EmailVerificationServiceImpl
        implements EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final BrevoService brevoService;
    private final UserRepository userRepository;


    // ==========================
    // CONSTRUCTOR
    // ==========================

    public EmailVerificationServiceImpl(
            EmailVerificationRepository emailVerificationRepository,
            BrevoService brevoService,
            UserRepository userRepository) {

        this.emailVerificationRepository = emailVerificationRepository;
        this.brevoService = brevoService;
        this.userRepository = userRepository;
    }


    // ==========================
    // SEND OTP
    // ==========================

    @Override
    public void sendOtp(User user) {

        // Generate 6-digit OTP
        String otp =
                String.valueOf(
                        100000 + new Random().nextInt(900000)
                );


        // Remove previous OTP if exists

        emailVerificationRepository
                .findByEmail(user.getEmail())
                .ifPresent(
                        emailVerificationRepository::delete
                );


        // Create verification record

        EmailVerification verification =
                new EmailVerification();

        verification.setEmail(user.getEmail());

        verification.setOtp(otp);

        verification.setVerified(false);

        verification.setExpiryTime(
                LocalDateTime.now().plusMinutes(10)
        );


        // Save OTP in database

        emailVerificationRepository.save(verification);


        // Email Content

        String html = """
                <html>
                <body>

                    <h2>Email Verification</h2>

                    <p>Hello,</p>

                    <p>
                        Please use the following OTP
                        to verify your email address:
                    </p>

                    <h1>%s</h1>

                    <p>
                        This OTP is valid for 10 minutes.
                    </p>

                    <p>
                        If you did not create this account,
                        please ignore this email.
                    </p>

                </body>
                </html>
                """.formatted(otp);


        // Send email using Brevo

        brevoService.sendEmail(
                user.getEmail(),
                "Verify Your Email - AI Resume Analyzer",
                html
        );
    }


    // ==========================
    // VERIFY OTP
    // ==========================

    @Override
    public String verifyOtp(
            String email,
            String otp) {


        // Find OTP

        EmailVerification verification =
                emailVerificationRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "OTP not found"
                                )
                        );


        // Already verified

        if (verification.isVerified()) {

            return "Email already verified";
        }


        // Check OTP

        if (!verification.getOtp().equals(otp)) {

            return "Invalid OTP";
        }


        // Check OTP expiry

        if (verification
                .getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            return "OTP Expired";
        }


        // Mark OTP as verified

        verification.setVerified(true);

        emailVerificationRepository.save(verification);


        // ==========================
        // ENABLE USER
        // ==========================

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );


        user.setEnabled(true);

        userRepository.save(user);


        return "Email Verified Successfully";
    }


    // ==========================
    // RESEND OTP
    // ==========================

    @Override
    public void resendOtp(String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );


        sendOtp(user);
    
}
}

