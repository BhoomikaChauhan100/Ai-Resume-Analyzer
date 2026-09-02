package com.example.ai_resume_analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ai_resume_analyzer.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUserEmail(String email);

}