package com.taskflow.service;

import com.taskflow.dto.request.CreateWorkspaceRequest;
import com.taskflow.dto.response.WorkspaceResponse;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.WorkspaceMapper;
import com.taskflow.model.User;
import com.taskflow.model.Workspace;
import com.taskflow.repository.UserRepository;
import com.taskflow.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

// Business logic for Workspace operations.

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final OwnershipService ownershipService;

    // Create a new workspace for the given owner.
    @Transactional
    public WorkspaceResponse create(CreateWorkspaceRequest request, UUID currentUserId) {
        User owner = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Owner not found: " + currentUserId));

        Workspace workspace = Workspace.builder()
                .name(request.getName())
                .owner(owner)
                .build();

        return WorkspaceMapper.toResponse(workspaceRepository.save(workspace));
    }

    @Transactional(readOnly = true)
    public WorkspaceResponse getById(UUID id, UUID currentUserId) {
        ownershipService.checkWorkspaceOwnership(id, currentUserId);
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found: " + id));
        return WorkspaceMapper.toResponse(workspace);
    }

    // List all workspaces owned by a given user
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> listByOwner(UUID ownerId) {
        return workspaceRepository.findByOwner_Id(ownerId).stream()
                .map(WorkspaceMapper::toResponse)
                .toList();
    }

    // Delete a workspace. Cascades to boards (see @OneToMany cascade on Workspace)
    @Transactional
    public void delete(UUID id, UUID currentUserId) {
        ownershipService.checkWorkspaceOwnership(id, currentUserId);
        workspaceRepository.deleteById(id);
    }
}