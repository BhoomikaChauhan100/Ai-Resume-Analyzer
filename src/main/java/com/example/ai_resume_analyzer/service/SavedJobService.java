package com.example.ai_resume_analyzer.service;

import java.util.List;

import com.example.ai_resume_analyzer.entity.SavedJob;

public interface SavedJobService {

	void saveJob(SavedJob savedJob, String email);

	List<SavedJob> getSavedJobs(String email);

	void deleteSavedJob(Long id, String email);
}