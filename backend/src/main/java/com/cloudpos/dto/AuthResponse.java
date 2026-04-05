package com.cloudpos.dto;

import com.cloudpos.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type;
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private User.UserRole role;
}