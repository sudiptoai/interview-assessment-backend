package com.code_editor.interview_assesement.dto;

import com.code_editor.interview_assesement.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private Boolean enabled;
    private Date createdAt;
    private Date updatedAt;
}
