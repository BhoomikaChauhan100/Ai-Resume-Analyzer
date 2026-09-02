package com.example.ai_resume_analyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ai_resume_analyzer.dto.AdzunaResponse;
import com.example.ai_resume_analyzer.dto.JobSearchRequest;
import com.example.ai_resume_analyzer.service.AdzunaService;

@Controller
public class JobRecommendationController {

    private final AdzunaService adzunaService;

    public JobRecommendationController(AdzunaService adzunaService) {
        this.adzunaService = adzunaService;
    }

    @GetMapping("/jobs/recommendations")
    public String recommendations(

            @RequestParam(
                    required = false,
                    defaultValue = "Java"
            )
            String keyword,

            @RequestParam(
                    required = false,
                    defaultValue = "Delhi"
            )
            String location,

            @RequestParam(
                    required = false,
                    defaultValue = "1"
            )
            int page,

            Model model) {

        JobSearchRequest request = new JobSearchRequest();

        request.setKeyword(keyword);
        request.setLocation(location);
        request.setPage(page);

        AdzunaResponse response =
                adzunaService.searchJobs(request);

        model.addAttribute(
                "jobs",
                response.getResults()
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        model.addAttribute(
                "location",
                location
        );

        model.addAttribute(
                "count",
                response.getCount()
        );

        // IMPORTANT
        model.addAttribute(
                "currentPage",
                page
        );

        return "jobs/recommendations";
    }
}