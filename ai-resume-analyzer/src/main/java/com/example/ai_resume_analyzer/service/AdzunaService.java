package com.example.ai_resume_analyzer.service;

import com.example.ai_resume_analyzer.dto.AdzunaResponse;
import com.example.ai_resume_analyzer.dto.JobSearchRequest;

public interface AdzunaService {

    AdzunaResponse searchJobs(JobSearchRequest request);
}