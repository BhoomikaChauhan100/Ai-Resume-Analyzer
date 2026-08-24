package com.example.ai_resume_analyzer.service;

import com.example.ai_resume_analyzer.dto.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(String email);

    void updateProfile(
            String email,
            String name,
            String phone
    );
}