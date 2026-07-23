package com.taskflow.mapper;

import com.taskflow.dto.response.BoardResponse;
import com.taskflow.model.Board;

public final class BoardMapper {

    private BoardMapper() {}

    public static BoardResponse toResponse(Board board) {
        if (board == null) return null;
        return BoardResponse.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .workspaceId(board.getWorkspace().getId())
                .createdAt(board.getCreatedAt())
                .build();
    }
}