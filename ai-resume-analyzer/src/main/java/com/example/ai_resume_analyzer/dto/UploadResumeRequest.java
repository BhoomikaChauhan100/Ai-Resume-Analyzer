package com.example.ai_resume_analyzer.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadResumeRequest {
	
	private String email;

	private MultipartFile file;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}


}
