package com.example.ai_resume_analyzer.serviceimpl;

import org.springframework.stereotype.Service;

import com.example.ai_resume_analyzer.dto.ProfileResponse;
import com.example.ai_resume_analyzer.entity.User;
import com.example.ai_resume_analyzer.repository.UserRepository;
import com.example.ai_resume_analyzer.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // =========================================
    // GET PROFILE
    // =========================================

    @Override
    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        ProfileResponse response = new ProfileResponse();

        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setProfileImage(user.getProfileImage());

        response.setRole(
                user.getRole() != null
                        ? user.getRole().name()
                        : null
        );

        response.setProvider(
                user.getProvider() != null
                        ? user.getProvider().name()
                        : null
        );

        response.setEnabled(user.isEnabled());

        response.setCreatedAt(
                user.getCreatedAt() != null
                        ? user.getCreatedAt().toString()
                        : null
        );

        return response;
    }


    // =========================================
    // UPDATE PROFILE
    // =========================================

    @Override
    public void updateProfile(
            String email,
            String name,
            String phone) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setName(name);
        user.setPhone(phone);

        userRepository.save(user);
    }
}