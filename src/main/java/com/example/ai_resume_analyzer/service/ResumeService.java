package com.example.ai_resume_analyzer.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.ai_resume_analyzer.dto.ResumeResponse;
import com.example.ai_resume_analyzer.dto.UploadResumeRequest;

public interface ResumeService {

    void uploadResume(
            UploadResumeRequest request,
            MultipartFile file);

    List<ResumeResponse> getAllResumes(
            String email);

    void deleteResume(
            long id,
            String email);
}