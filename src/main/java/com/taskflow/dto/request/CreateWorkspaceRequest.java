package com.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWorkspaceRequest {

    @NotBlank(message = "Workspace name is required")
    @Size(min = 1, max = 100)
    private String name;

    /**
     * The user who will own this workspace.
     * TEMPORARY: once JWT is added, this will come from the authenticated
     * principal (@AuthenticationPrincipal) and this field will be removed.
     */
    private UUID ownerId;
}