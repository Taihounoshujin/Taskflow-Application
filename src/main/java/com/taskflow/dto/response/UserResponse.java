package com.taskflow.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Safe outbound representation of a User.
 * Notably excludes passwordHash — never expose that, even hashed.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String displayName;
    private LocalDateTime createdAt;
}
