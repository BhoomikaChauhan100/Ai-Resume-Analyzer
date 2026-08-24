package com.example.ai_resume_analyzer.service;

import com.example.ai_resume_analyzer.dto.LoginRequest;
import com.example.ai_resume_analyzer.dto.LoginResponse;
import com.example.ai_resume_analyzer.dto.RegisterRequest;
import com.example.ai_resume_analyzer.dto.RegisterResponse;

public interface AuthService {
	RegisterResponse register(RegisterRequest request);

	LoginResponse login(LoginRequest request);

}
