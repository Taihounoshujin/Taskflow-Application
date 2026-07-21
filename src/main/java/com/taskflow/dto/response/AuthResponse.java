package com.taskflow.dto.response;

import lombok.*;

import java.util.UUID;

/**
 * Returned by POST /api/auth/login on success.
 * The client stores the token and sends it in the Authorization header
 * on every subsequent request.
 */
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