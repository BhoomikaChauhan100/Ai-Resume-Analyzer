package com.example.ai_resume_analyzer.serviceimpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ai_resume_analyzer.dto.LoginRequest;
import com.example.ai_resume_analyzer.dto.LoginResponse;
import com.example.ai_resume_analyzer.dto.RegisterRequest;
import com.example.ai_resume_analyzer.dto.RegisterResponse;
import com.example.ai_resume_analyzer.entity.User;
import com.example.ai_resume_analyzer.enums.Provider;
import com.example.ai_resume_analyzer.enums.Role;
import com.example.ai_resume_analyzer.jwt.JwtService;
import com.example.ai_resume_analyzer.repository.UserRepository;
import com.example.ai_resume_analyzer.security.CustomUserDetailsService;
import com.example.ai_resume_analyzer.service.AuthService;
import com.example.ai_resume_analyzer.service.EmailVerificationService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailVerificationService emailVerificationService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService,
            EmailVerificationService emailVerificationService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.emailVerificationService = emailVerificationService;
    }

    // ==========================
    // REGISTER USER
    // ==========================
    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // Encrypt Password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setProvider(Provider.LOCAL);
        user.setRole(Role.USER);

        // User will be enabled after OTP verification
        user.setEnabled(false);

        // Save User
        userRepository.save(user);

        // Send OTP to Email
        emailVerificationService.sendOtp(user);

       return new RegisterResponse(
    "Registration Successful. Please verify your email using the OTP sent to your email.",
    user.getName(),
    user.getEmail());
    }

    // ==========================
    // LOGIN USER
    // ==========================
    @Override
    public LoginResponse login(LoginRequest request) {

        // Authenticate Email & Password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // Load User
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check Email Verification
        if (!user.isEnabled()) {
            throw new RuntimeException("Please verify your email before login.");
        }

        // Load UserDetails
        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(request.getEmail());

        // Generate JWT
        String token = jwtService.generateToken(userDetails);

        // Prepare Response
        LoginResponse response = new LoginResponse();
        response.setMessage("Login Successful");
        response.setToken(token);
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        return response;
    }
}