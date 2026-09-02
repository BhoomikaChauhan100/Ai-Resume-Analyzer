
package com.example.ai_resume_analyzer.service;

import com.example.ai_resume_analyzer.dto.ResumeAnalysisResponse;

public interface GeminiService {

    ResumeAnalysisResponse analyzeResume(String resumeText);

}