package com.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCardRequest {

    @NotBlank(message = "Card title is required")
    @Size(min = 1, max = 200)
    private String title;

    @Size(max = 2000)
    private String description;

    private LocalDateTime dueDate;

    /** null to create unassigned. */
    private UUID assigneeId;
}
