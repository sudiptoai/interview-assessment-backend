package com.code_editor.interview_assesement.services;

import com.code_editor.interview_assesement.dto.AuthResponseDTO;
import com.code_editor.interview_assesement.dto.UserLoginDTO;
import com.code_editor.interview_assesement.dto.UserResponseDTO;
import com.code_editor.interview_assesement.exceptions.InvalidCredentialsException;
import com.code_editor.interview_assesement.models.User;
import com.code_editor.interview_assesement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of AuthService following Single Responsibility Principle
 * This class is responsible only for authentication operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Override
    public AuthResponseDTO login(UserLoginDTO loginDTO) {
        log.info("Login attempt for: {}", loginDTO.getEmailOrUsername());
        
        // Find user by email or username
        Optional<User> userOptional = userService.findByEmailOrUsername(loginDTO.getEmailOrUsername());
        
        if (userOptional.isEmpty()) {
            log.warn("Login failed: User not found - {}", loginDTO.getEmailOrUsername());
            throw new InvalidCredentialsException("Invalid email/username or password");
        }
        
        User user = userOptional.get();
        
        // Validate password
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            log.warn("Login failed: Invalid password for user - {}", loginDTO.getEmailOrUsername());
            throw new InvalidCredentialsException("Invalid email/username or password");
        }
        
        // Check if user is enabled
        if (!user.getEnabled()) {
            log.warn("Login failed: User account is disabled - {}", loginDTO.getEmailOrUsername());
            throw new InvalidCredentialsException("User account is disabled");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        log.info("Login successful for user: {}", user.getUsername());
        
        // Convert user to DTO
        UserResponseDTO userDTO = userService.convertToDTO(user);
        
        return new AuthResponseDTO(token, userDTO);
    }
}
