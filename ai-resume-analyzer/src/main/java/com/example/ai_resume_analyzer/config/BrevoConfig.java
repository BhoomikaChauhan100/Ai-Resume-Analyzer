package com.example.ai_resume_analyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BrevoConfig {

    @Bean
    public WebClient brevoWebClient() {

        return WebClient.builder()
                .baseUrl("https://api.brevo.com")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

}