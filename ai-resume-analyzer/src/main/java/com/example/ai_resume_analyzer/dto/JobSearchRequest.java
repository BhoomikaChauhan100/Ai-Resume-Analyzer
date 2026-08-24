package com.example.ai_resume_analyzer.dto;

public class JobSearchRequest {

    private String keyword;
    private String location;
    private int page;

    public JobSearchRequest() {
        this.page = 1;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}