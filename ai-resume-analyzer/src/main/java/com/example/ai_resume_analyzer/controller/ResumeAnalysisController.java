package com.example.ai_resume_analyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ai_resume_analyzer.dto.ResumeAnalysisResponse;
import com.example.ai_resume_analyzer.entity.Resume;
import com.example.ai_resume_analyzer.repository.ResumeRepository;
import com.example.ai_resume_analyzer.service.GeminiService;
import com.example.ai_resume_analyzer.service.PdfService;

@Controller
public class ResumeAnalysisController {

    private final ResumeRepository resumeRepository;
    private final PdfService pdfService;
    private final GeminiService geminiService;

    public ResumeAnalysisController(
            ResumeRepository resumeRepository,
            PdfService pdfService,
            GeminiService geminiService) {

        this.resumeRepository = resumeRepository;
        this.pdfService = pdfService;
        this.geminiService = geminiService;
    }

    @GetMapping("/resume/analyze/{id}")
    public String analyzeResume(
            @PathVariable Long id,
            Model model) {

        System.out.println("STEP 1");

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resume Not Found"));

        System.out.println("STEP 2");
        System.out.println("File Path = " + resume.getFilePath());

        String resumeText = pdfService.extractText(resume.getFilePath());

        System.out.println("STEP 3");
        System.out.println("Resume Length = " + resumeText.length());

        ResumeAnalysisResponse response =
                geminiService.analyzeResume(resumeText);

        System.out.println("STEP 4");

        model.addAttribute("analysis", response);

        return "resume/analysis-result";
    }

}