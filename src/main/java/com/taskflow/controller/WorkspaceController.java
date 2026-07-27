package com.taskflow.controller;

import com.taskflow.dto.request.CreateWorkspaceRequest;
import com.taskflow.dto.response.WorkspaceResponse;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final UserRepository userRepository;

    /** Helper: resolve the current user's UUID from the JWT principal. */
    private UUID getCurrentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"))
                .getId();
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> create(
            @Valid @RequestBody CreateWorkspaceRequest request,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        return new ResponseEntity<>(workspaceService.create(request, currentUserId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(workspaceService.getById(id, getCurrentUserId(principal)));
    }

    @GetMapping("/me")
    public ResponseEntity<List<WorkspaceResponse>> listMine(
            @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(workspaceService.listByOwner(getCurrentUserId(principal)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal) {
        workspaceService.delete(id, getCurrentUserId(principal));
        return ResponseEntity.noContent().build();
    }
}