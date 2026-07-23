package com.taskflow.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnResponse {
    private UUID id;
    private String name;
    private Integer position;
    private UUID boardId;
}