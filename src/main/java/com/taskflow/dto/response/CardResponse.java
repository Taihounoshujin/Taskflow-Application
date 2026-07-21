package com.taskflow.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardResponse {
    private UUID id;
    private String title;
    private String description;
    private Integer position;
    private LocalDateTime dueDate;
    private UUID columnId;
    private UUID assigneeId;      // nullable
    private Set<UUID> labelIds;   // flat IDs; expand into full DTOs later if needed
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
