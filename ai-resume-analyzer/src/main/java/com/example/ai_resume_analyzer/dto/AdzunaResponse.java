package com.example.ai_resume_analyzer.dto;

import java.util.List;

public class AdzunaResponse {

    private int count;

    private List<AdzunaJob> results;

    public AdzunaResponse() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AdzunaJob> getResults() {
        return results;
    }

    public void setResults(List<AdzunaJob> results) {
        this.results = results;
    }
}