package com.code_editor.interview_assesement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private UserResponseDTO user;
    
    public AuthResponseDTO(String token, UserResponseDTO user) {
        this.token = token;
        this.type = "Bearer";
        this.user = user;
    }
}
