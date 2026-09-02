package com.example.ai_resume_analyzer.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ai_resume_analyzer.dto.UploadResumeRequest;
import com.example.ai_resume_analyzer.service.ResumeService;

@Controller
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // =========================
    // UPLOAD PAGE
    // =========================

    @GetMapping("/resume/upload")
    public String uploadPage() {
        return "resume/upload";
    }

    // =========================
    // UPLOAD RESUME
    // =========================

    @PostMapping("/resume/upload")
    public String uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication,
            Model model) {

        try {

            String email = authentication.getName();

            UploadResumeRequest request =
                    new UploadResumeRequest();

            request.setEmail(email);

            resumeService.uploadResume(
                    request,
                    file
            );

            model.addAttribute(
                    "message",
                    "Resume Uploaded Successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "resume/upload";
    }

    // =========================
    // MY RESUMES
    // =========================

    @GetMapping("/resume/list")
    public String getAllResumes(
            Authentication authentication,
            Model model) {

        if (authentication == null ||
            !authentication.isAuthenticated()) {

            return "redirect:/auth/login";
        }

        String email = authentication.getName();

        model.addAttribute(
                "resumes",
                resumeService.getAllResumes(email)
        );

        return "resume/list";
    }

    // =========================
    // DELETE RESUME
    // =========================

    @PostMapping("/resume/delete/{id}")
    public String deleteResume(
            @PathVariable long id,
            Authentication authentication) {

        if (authentication == null ||
            !authentication.isAuthenticated()) {

            return "redirect:/auth/login";
        }

        String email = authentication.getName();

        resumeService.deleteResume(id, email);

        return "redirect:/resume/list";
    }
}