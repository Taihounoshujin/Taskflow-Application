package com.taskflow.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard error envelope returned by @ControllerAdvice.
 * Keeps API errors consistent — clients can rely on the shape.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    /** Populated for validation errors — one entry per invalid field. */
    private List<String> details;
}
