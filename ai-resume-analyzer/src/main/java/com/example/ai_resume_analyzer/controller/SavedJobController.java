package com.example.ai_resume_analyzer.controller;

import org.springframework.security.core.Authentication;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.ai_resume_analyzer.entity.SavedJob;
import com.example.ai_resume_analyzer.service.SavedJobService;

@Controller
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(
            SavedJobService savedJobService) {

        this.savedJobService = savedJobService;
    }


    // =========================================================
    // SAVE JOB
    // =========================================================

    @PostMapping("/jobs/save")
    @ResponseBody
    public Map<String, Object> saveJob(
            @ModelAttribute SavedJob savedJob,
            Authentication authentication) {

        String email = authentication.getName();

        savedJobService.saveJob(savedJob, email);

        Map<String, Object> response = new HashMap<>();

        response.put("success", true);
        response.put("message", "Job saved successfully!");

        return response;
    }


    // =========================================================
    // VIEW SAVED JOBS
    // =========================================================

    @GetMapping("/jobs/saved")
    public String savedJobs(

            Authentication authentication,

            Model model) {


        // Get logged-in user's email

        String email = authentication.getName();


        // Get only this user's saved jobs

        model.addAttribute(
                "savedJobs",
                savedJobService.getSavedJobs(email)
        );


        // Open saved-jobs.html

        return "jobs/saved-jobs";
    }


    // =========================================================
    // DELETE SAVED JOB
    // =========================================================

    @PostMapping("/jobs/delete/{id}")
    public String deleteSavedJob(

            @PathVariable Long id,

            Authentication authentication) {


        String email = authentication.getName();


        savedJobService.deleteSavedJob(
                id,
                email
        );


        // After deleting, remain on Saved Jobs page

        return "redirect:/jobs/saved";
    }
}