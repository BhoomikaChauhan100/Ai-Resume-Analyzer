package com.example.ai_resume_analyzer.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ai_resume_analyzer.client.AdzunaClient;
import com.example.ai_resume_analyzer.dto.AdzunaResponse;
import com.example.ai_resume_analyzer.dto.JobSearchRequest;
import com.example.ai_resume_analyzer.service.AdzunaService;

@Service
public class AdzunaServiceImpl implements AdzunaService {

    private final AdzunaClient adzunaClient;

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.api.key}")
    private String apiKey;

    @Value("${adzuna.country}")
    private String country;

    public AdzunaServiceImpl(AdzunaClient adzunaClient) {
        this.adzunaClient = adzunaClient;
    }

    @Override
    public AdzunaResponse searchJobs(JobSearchRequest request) {

        return adzunaClient.searchJobs(
                appId,
                apiKey,
                country,
                request.getPage(),
                request.getKeyword(),
                request.getLocation()
        );
    }
}