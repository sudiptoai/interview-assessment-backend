package com.code_editor.interview_assesement.services;

import com.code_editor.interview_assesement.dto.UserRegistrationDTO;
import com.code_editor.interview_assesement.dto.UserResponseDTO;
import com.code_editor.interview_assesement.models.User;

import java.util.Optional;

/**
 * Service interface for User operations following the Interface Segregation Principle (ISP)
 * and Dependency Inversion Principle (DIP)
 */
public interface UserService {
    
    /**
     * Register a new user
     * @param registrationDTO User registration data
     * @return UserResponseDTO containing created user details
     */
    UserResponseDTO registerUser(UserRegistrationDTO registrationDTO);
    
    /**
     * Find user by email
     * @param email User email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find user by username
     * @param username Username
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email or username
     * @param emailOrUsername Email or username
     * @return Optional<User>
     */
    Optional<User> findByEmailOrUsername(String emailOrUsername);
    
    /**
     * Convert User entity to UserResponseDTO
     * @param user User entity
     * @return UserResponseDTO
     */
    UserResponseDTO convertToDTO(User user);
}
