package com.example.ai_resume_analyzer.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ai_resume_analyzer.dto.GeminiRequest;
import com.example.ai_resume_analyzer.dto.GeminiResponse;
import com.example.ai_resume_analyzer.dto.ResumeAnalysisResponse;
import com.example.ai_resume_analyzer.service.GeminiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiServiceImpl implements GeminiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    public GeminiServiceImpl(WebClient geminiWebClient) {
        this.webClient = geminiWebClient;
    }

    @Override
    public ResumeAnalysisResponse analyzeResume(String resumeText) {

        String prompt = """
You are an ATS Resume Analyzer.

Analyze the following resume.

Return ONLY valid JSON.
Do NOT return markdown.
Do NOT return explanation.

Use exactly this format:

{
  "atsScore": 0,
  "summary": "",
  "strengths": [],
  "missingKeywords": [],
  "improvements": []
}

Resume:

""" + resumeText;

        GeminiRequest.Part part = new GeminiRequest.Part(prompt);
        GeminiRequest.Content content = new GeminiRequest.Content(List.of(part));
        GeminiRequest request = new GeminiRequest(List.of(content));

        try {

            GeminiResponse response = webClient.post()
                    .uri("/v1beta/models/" + model + ":generateContent?key=" + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block();

            if (response == null
                    || response.getCandidates() == null
                    || response.getCandidates().isEmpty()) {

                throw new RuntimeException("Gemini returned an empty response.");
            }

            String json = response.getCandidates()
                    .get(0)
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();

            if (json == null || json.isBlank()) {
                throw new RuntimeException("Gemini returned empty text.");
            }

            json = json.replace("```json", "")
                       .replace("```", "")
                       .trim();

            return objectMapper.readValue(json, ResumeAnalysisResponse.class);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("Gemini Analysis Failed: " + e.getMessage());

        }
    }
}