package com.example.ai_resume_analyzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdzunaJob {

    private Long id;

    private String title;

    private Company company;

    private Location location;

    private String description;

    @JsonProperty("salary_min")
    private Double salaryMin;

    @JsonProperty("salary_max")
    private Double salaryMax;

    @JsonProperty("contract_time")
    private String contractTime;

    @JsonProperty("redirect_url")
    private String redirectUrl;


    public AdzunaJob() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }


    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }


    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }


    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    public static class Company {

        @JsonProperty("display_name")
        private String displayName;


        public Company() {
        }


        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }


    public static class Location {

        @JsonProperty("display_name")
        private String displayName;


        public Location() {
        }


        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
}