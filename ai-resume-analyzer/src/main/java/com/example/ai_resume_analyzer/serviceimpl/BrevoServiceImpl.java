package com.example.ai_resume_analyzer.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ai_resume_analyzer.dto.BrevoRequest;
import com.example.ai_resume_analyzer.service.BrevoService;

@Service
public class BrevoServiceImpl implements BrevoService {

    private final WebClient webClient;

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    public BrevoServiceImpl(@Qualifier("brevoWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void sendEmail(String to, String subject, String htmlContent) {

        BrevoRequest.Sender sender =
                new BrevoRequest.Sender(senderName, senderEmail);

        BrevoRequest.Receiver receiver =
                new BrevoRequest.Receiver(to);

        BrevoRequest request = new BrevoRequest(
                sender,
                List.of(receiver),
                subject,
                htmlContent
        );

        String response = webClient.post()
                .uri("/v3/smtp/email")
                .header("api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);
    }
}