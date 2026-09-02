package com.example.ai_resume_analyzer.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

import com.example.ai_resume_analyzer.dto.LoginRequest;
import com.example.ai_resume_analyzer.dto.LoginResponse;
import com.example.ai_resume_analyzer.dto.RegisterRequest;
import com.example.ai_resume_analyzer.dto.RegisterResponse;
import com.example.ai_resume_analyzer.service.AuthService;
import com.example.ai_resume_analyzer.service.EmailVerificationService;

@Controller
public class AuthController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            AuthService authService,
            EmailVerificationService emailVerificationService,
            AuthenticationManager authenticationManager) {

        this.authService = authService;
        this.emailVerificationService = emailVerificationService;
        this.authenticationManager = authenticationManager;
    }

    // ==========================
    // HOME
    // ==========================

    @GetMapping("/")
    public String home() {
        return "index";
    }


    // ==========================
    // REGISTER PAGE
    // ==========================

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute(
                "registerRequest",
                new RegisterRequest()
        );

        return "auth/register";
    }


    // ==========================
    // REGISTER
    // ==========================

    @PostMapping("/register")
    public String register(
            @ModelAttribute RegisterRequest request,
            Model model) {

        RegisterResponse response =
                authService.register(request);

        model.addAttribute(
                "success",
                response.getMessage()
        );

        model.addAttribute(
                "email",
                request.getEmail()
        );

        return "auth/verify-otp";
    }


    // ==========================
    // VERIFY OTP
    // ==========================

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam String email,
            @RequestParam String otp,
            Model model) {

        String result =
                emailVerificationService.verifyOtp(
                        email,
                        otp
                );

        if (result.equals(
                "Email Verified Successfully")) {

            model.addAttribute(
                    "success",
                    result
            );

            model.addAttribute(
                    "loginRequest",
                    new LoginRequest()
            );

            return "auth/login";
        }

        model.addAttribute(
                "error",
                result
        );

        model.addAttribute(
                "email",
                email
        );

        return "auth/verify-otp";
    }


    // ==========================
    // LOGIN PAGE
    // ==========================

    @GetMapping("/auth/login")
    public String loginPage(Model model) {

        model.addAttribute(
                "loginRequest",
                new LoginRequest()
        );

        return "auth/login";
    }


    // ==========================
    // LOGIN
    // ==========================

    @PostMapping("/auth/login")
    public String login(
            @ModelAttribute LoginRequest request,
            Model model,
            HttpServletResponse response) {

        // --------------------------------
        // Authenticate using your service
        // --------------------------------

        LoginResponse loginResponse =
                authService.login(request);


        // --------------------------------
        // PUT JWT INTO HTTP-ONLY COOKIE
        // --------------------------------

        ResponseCookie jwtCookie =
                ResponseCookie.from(
                        "JWT",
                        loginResponse.getToken()
                )
                .httpOnly(true)
                .secure(false)       // localhost = false
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();


        response.addHeader(
                "Set-Cookie",
                jwtCookie.toString()
        );


        // --------------------------------
        // Dashboard data
        // --------------------------------

        model.addAttribute(
                "name",
                loginResponse.getName()
        );

        model.addAttribute(
                "email",
                loginResponse.getEmail()
        );

        model.addAttribute(
                "message",
                loginResponse.getMessage()
        );


        return "dashboard/dashboard";
    }
}