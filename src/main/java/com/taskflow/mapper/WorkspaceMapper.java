package com.taskflow.mapper;

import com.taskflow.dto.response.WorkspaceResponse;
import com.taskflow.model.Workspace;

public final class WorkspaceMapper {

    private WorkspaceMapper() {}

    public static WorkspaceResponse toResponse(Workspace workspace) {
        if (workspace == null) return null;
        return WorkspaceResponse.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .ownerId(workspace.getOwner().getId())
                .createdAt(workspace.getCreatedAt())
                .build();
    }
}