package com.taskflow.mapper;

import com.taskflow.dto.response.ColumnResponse;
import com.taskflow.model.BoardColumn;

public final class ColumnMapper {

    private ColumnMapper() {}

    public static ColumnResponse toResponse(BoardColumn column) {
        if (column == null) return null;
        return ColumnResponse.builder()
                .id(column.getId())
                .name(column.getName())
                .position(column.getPosition())
                .boardId(column.getBoard().getId())
                .build();
    }
}