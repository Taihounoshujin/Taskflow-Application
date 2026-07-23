package com.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateColumnRequest {

    @NotBlank(message = "Column name is required")
    @Size(min = 1, max = 100)
    private String name;
}