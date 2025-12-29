package com.code_editor.interview_assesement.services;

import com.code_editor.interview_assesement.dto.UserRegistrationDTO;
import com.code_editor.interview_assesement.dto.UserResponseDTO;
import com.code_editor.interview_assesement.exceptions.UserAlreadyExistsException;
import com.code_editor.interview_assesement.models.User;
import com.code_editor.interview_assesement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of UserService following Single Responsibility Principle
 * This class is responsible only for user management operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        log.info("Attempting to register user with email: {}", registrationDTO.getEmail());
        
        // Check if user already exists
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            log.warn("Registration failed: Email already exists - {}", registrationDTO.getEmail());
            throw new UserAlreadyExistsException("Email already registered: " + registrationDTO.getEmail());
        }
        
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            log.warn("Registration failed: Username already exists - {}", registrationDTO.getUsername());
            throw new UserAlreadyExistsException("Username already taken: " + registrationDTO.getUsername());
        }
        
        // Create new user
        User user = User.builder()
                .username(registrationDTO.getUsername())
                .email(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .role(registrationDTO.getRole())
                .enabled(true)
                .build();
        
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        
        return convertToDTO(savedUser);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public Optional<User> findByEmailOrUsername(String emailOrUsername) {
        // Try to find by email first, then by username
        Optional<User> user = findByEmail(emailOrUsername);
        if (user.isEmpty()) {
            user = findByUsername(emailOrUsername);
        }
        return user;
    }
    
    @Override
    public UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
