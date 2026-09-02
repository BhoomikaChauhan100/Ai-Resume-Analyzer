package com.example.ai_resume_analyzer.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ai_resume_analyzer.dto.AdzunaResponse;

@Component
public class AdzunaClient {

    private final WebClient adzunaWebClient;

    public AdzunaClient(
            @Qualifier("adzunaWebClient") WebClient adzunaWebClient) {

        this.adzunaWebClient = adzunaWebClient;
    }

    public AdzunaResponse searchJobs(
            String appId,
            String apiKey,
            String country,
            int page,
            String keyword,
            String location) {

        return adzunaWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/api/jobs/{country}/search/{page}")
                        .queryParam("app_id", appId)
                        .queryParam("app_key", apiKey)
                        .queryParam("results_per_page", 10)
                        .queryParam("what", keyword)
                        .queryParam("where", location)
                        .queryParam("content-type", "application/json")
                        .build(country, page))
                .retrieve()
                .bodyToMono(AdzunaResponse.class)
                .block();
    }
}