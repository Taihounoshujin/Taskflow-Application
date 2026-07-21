package com.taskflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Payload for POST /api/users/register.
 * <p>
 * Using a DTO (not the entity) means:
 *   - clients can't accidentally set id, createdAt, or other server-managed fields
 *   - validation lives on the API contract, not the persistence model
 *   - the API shape can evolve independently of the DB schema
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 50)
    private String displayName;
}
