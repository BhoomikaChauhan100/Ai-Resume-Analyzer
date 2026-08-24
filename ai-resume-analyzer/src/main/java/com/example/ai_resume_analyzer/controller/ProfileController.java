package com.example.ai_resume_analyzer.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ai_resume_analyzer.dto.ProfileResponse;
import com.example.ai_resume_analyzer.service.ProfileService;

@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    // =========================================
    // VIEW PROFILE
    // =========================================

    @GetMapping("/profile")
    public String profile(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        ProfileResponse profile =
                profileService.getProfile(email);

        model.addAttribute(
                "profile",
                profile
        );

        return "profile/profile";
    }


    // =========================================
    // UPDATE PROFILE
    // =========================================

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam String name,
            @RequestParam(required = false) String phone,
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        profileService.updateProfile(
                email,
                name,
                phone
        );

        ProfileResponse profile =
                profileService.getProfile(email);

        model.addAttribute(
                "profile",
                profile
        );

        model.addAttribute(
                "success",
                "Profile updated successfully!"
        );

        return "profile/profile";
    }
}