package com.taskflow.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceResponse {
    private UUID id;
    private String name;
    private UUID ownerId;
    private LocalDateTime createdAt;
}
