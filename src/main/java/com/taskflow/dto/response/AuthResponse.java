package com.taskflow.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String tokenType;   // Always "Bearer"
    private UUID userId;
    private String email;
    private String displayName;
}