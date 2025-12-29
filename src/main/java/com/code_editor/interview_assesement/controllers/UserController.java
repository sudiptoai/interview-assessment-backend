package com.code_editor.interview_assesement.controllers;

import com.code_editor.interview_assesement.dto.UserRegistrationDTO;
import com.code_editor.interview_assesement.dto.UserResponseDTO;
import com.code_editor.interview_assesement.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for User-related operations
 * Follows REST best practices and SOLID principles
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    /**
     * Register a new user
     * @param registrationDTO User registration data
     * @return ResponseEntity with created user details
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        log.info("POST /api/users/register - Registering new user");
        UserResponseDTO response = userService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
