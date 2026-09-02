package com.example.ai_resume_analyzer.service;

import com.example.ai_resume_analyzer.entity.User;

public interface EmailVerificationService {

	void sendOtp(User user);

	String verifyOtp(String email, String otp);

	void resendOtp(String email);

}