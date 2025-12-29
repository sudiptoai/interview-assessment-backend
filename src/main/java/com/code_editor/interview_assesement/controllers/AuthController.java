package com.code_editor.interview_assesement.controllers;

import com.code_editor.interview_assesement.dto.AuthResponseDTO;
import com.code_editor.interview_assesement.dto.UserLoginDTO;
import com.code_editor.interview_assesement.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Authentication operations
 * Handles user login and token generation
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Login endpoint - authenticates user and returns JWT token
     * @param loginDTO Login credentials
     * @return ResponseEntity with JWT token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        log.info("POST /api/auth/login - User login attempt");
        AuthResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Signin endpoint - alias for login
     * @param loginDTO Login credentials
     * @return ResponseEntity with JWT token and user details
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> signin(@Valid @RequestBody UserLoginDTO loginDTO) {
        log.info("POST /api/auth/signin - User signin attempt");
        AuthResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}
