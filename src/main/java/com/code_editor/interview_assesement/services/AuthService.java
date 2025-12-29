package com.code_editor.interview_assesement.services;

import com.code_editor.interview_assesement.dto.UserLoginDTO;
import com.code_editor.interview_assesement.dto.AuthResponseDTO;

/**
 * Service interface for Authentication operations following the Single Responsibility Principle (SRP)
 */
public interface AuthService {
    
    /**
     * Authenticate user and generate JWT token
     * @param loginDTO Login credentials
     * @return AuthResponseDTO containing token and user details
     */
    AuthResponseDTO login(UserLoginDTO loginDTO);
}
