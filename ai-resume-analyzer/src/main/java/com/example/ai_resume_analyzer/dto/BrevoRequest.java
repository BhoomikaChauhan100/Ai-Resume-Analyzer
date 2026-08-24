package com.example.ai_resume_analyzer.dto;

import java.util.List;

public class BrevoRequest {

    private Sender sender;
    private List<Receiver> to;
    private String subject;
    private String htmlContent;

    public BrevoRequest() {
    }

    public BrevoRequest(Sender sender, List<Receiver> to, String subject, String htmlContent) {
        this.sender = sender;
        this.to = to;
        this.subject = subject;
        this.htmlContent = htmlContent;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public List<Receiver> getTo() {
        return to;
    }

    public void setTo(List<Receiver> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public static class Sender {

        private String name;
        private String email;

        public Sender() {
        }

        public Sender(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class Receiver {

        private String email;

        public Receiver() {
        }

        public Receiver(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}