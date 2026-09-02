package com.example.ai_resume_analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ai_resume_analyzer.entity.SavedJob;

public interface SavedJobRepository
        extends JpaRepository<SavedJob, Long> {

    List<SavedJob> findByUserEmail(String email);

}