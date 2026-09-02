package com.example.ai_resume_analyzer.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ai_resume_analyzer.entity.SavedJob;
import com.example.ai_resume_analyzer.entity.User;
import com.example.ai_resume_analyzer.repository.SavedJobRepository;
import com.example.ai_resume_analyzer.repository.UserRepository;
import com.example.ai_resume_analyzer.service.SavedJobService;

@Service
public class SavedJobServiceImpl
        implements SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final UserRepository userRepository;

    public SavedJobServiceImpl(
            SavedJobRepository savedJobRepository,
            UserRepository userRepository) {

        this.savedJobRepository = savedJobRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void saveJob(
            SavedJob savedJob,
            String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        savedJob.setUser(user);
        savedJob.setSavedAt(
                LocalDateTime.now());

        savedJobRepository.save(savedJob);
    }


    @Override
    public List<SavedJob> getSavedJobs(
            String email) {

        return savedJobRepository
                .findByUserEmail(email);
    }


    @Override
    public void deleteSavedJob(
            Long id,
            String email) {

        SavedJob savedJob =
                savedJobRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Saved job not found"));

        if (savedJob.getUser() == null ||
            !savedJob.getUser()
                    .getEmail()
                    .equals(email)) {

            throw new RuntimeException(
                    "You are not allowed to delete this job");
        }

        savedJobRepository.delete(savedJob);
    }
}