package com.taskflow.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID workspaceId;
    private LocalDateTime createdAt;
}
