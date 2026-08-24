package com.example.ai_resume_analyzer.serviceimpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ai_resume_analyzer.dto.ResumeResponse;
import com.example.ai_resume_analyzer.dto.UploadResumeRequest;
import com.example.ai_resume_analyzer.entity.Resume;
import com.example.ai_resume_analyzer.entity.User;
import com.example.ai_resume_analyzer.repository.ResumeRepository;
import com.example.ai_resume_analyzer.repository.UserRepository;
import com.example.ai_resume_analyzer.service.ResumeService;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeServiceImpl(
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    // =========================
    // UPLOAD RESUME
    // =========================

    @Override
    public void uploadResume(
            UploadResumeRequest request,
            MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException(
                        "Please select a resume file."
                );
            }

            String uploadDir =
                    "C:/resume_uploads/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =
                    file.getOriginalFilename();

            if (fileName == null ||
                fileName.isBlank()) {

                throw new RuntimeException(
                        "Invalid file name."
                );
            }

            File destination =
                    new File(dir, fileName);

            System.out.println(
                    "Saving File To: "
                    + destination.getAbsolutePath()
            );

            file.transferTo(destination);

            User user =
                    userRepository
                    .findByEmail(request.getEmail())
                    .orElseThrow(() ->
                        new RuntimeException(
                            "User not found: "
                            + request.getEmail()
                        )
                    );

            Resume resume = new Resume();

            resume.setFileName(fileName);

            resume.setFileType(
                    file.getContentType()
            );

            resume.setFilePath(
                    destination.getAbsolutePath()
            );

            resume.setUploadedAt(
                    LocalDateTime.now()
            );

            resume.setUser(user);

            resumeRepository.save(resume);

            System.out.println(
                    "Resume Uploaded Successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to upload file: "
                    + e.getMessage()
            );
        }
    }

    // =========================
    // GET ALL RESUMES
    // =========================

    @Override
    public List<ResumeResponse> getAllResumes(
            String email) {

        List<Resume> resumes =
                resumeRepository
                .findByUserEmail(email);

        List<ResumeResponse> responseList =
                new ArrayList<>();

        for (Resume resume : resumes) {

            ResumeResponse response =
                    new ResumeResponse();

            response.setId(resume.getId());

            response.setFileName(
                    resume.getFileName()
            );

            response.setFileType(
                    resume.getFileType()
            );

            response.setUploadedAt(
                    resume.getUploadedAt()
            );

            responseList.add(response);
        }

        return responseList;
    }

    // =========================
    // DELETE RESUME
    // =========================

    @Override
    public void deleteResume(
            long id,
            String email) {

        Resume resume =
                resumeRepository
                .findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Resume not found."
                    )
                );

        // Security check
        if (resume.getUser() == null ||
            !resume.getUser()
                   .getEmail()
                   .equals(email)) {

            throw new RuntimeException(
                    "You are not allowed to delete this resume."
            );
        }

        // Delete physical file
        String filePath =
                resume.getFilePath();

        if (filePath != null &&
            !filePath.isBlank()) {

            File file =
                    new File(filePath);

            if (file.exists()) {

                boolean deleted =
                        file.delete();

                System.out.println(
                    "Physical file deleted: "
                    + deleted
                );
            }
        }

        // Delete database record
        resumeRepository.delete(resume);

        System.out.println(
                "Resume deleted successfully: "
                + id
        );
    }
}