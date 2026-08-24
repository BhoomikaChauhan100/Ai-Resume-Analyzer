package com.example.ai_resume_analyzer.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ai_resume_analyzer.entity.User;

/**
 * Repository interface for User entity.
 *
 * JpaRepository provides built-in CRUD operations.
 */

public interface UserRepository extends JpaRepository<User,UUID>{
	
	/**
     * Find user by email.
     * Used during login.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check whether a user already exists with the given email.
     * Used during registration.
     */
    boolean existsByEmail(String email);

}
