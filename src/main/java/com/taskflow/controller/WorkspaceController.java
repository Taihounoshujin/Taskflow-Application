package com.taskflow.controller;

import com.taskflow.dto.request.CreateWorkspaceRequest;
import com.taskflow.dto.response.WorkspaceResponse;
import com.taskflow.model.User;
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

    /**
     * POST /api/workspaces — create a workspace owned by the current user.
     * ownerId is now derived from the JWT, not the request body.
     */
    @PostMapping
    public ResponseEntity<WorkspaceResponse> create(
            @Valid @RequestBody CreateWorkspaceRequest request,
            @AuthenticationPrincipal UserDetails principal) {

        // Look up the User entity by the email in the JWT.
        User currentUser = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

        // Overwrite whatever the client sent — never trust the client for identity.
        request.setOwnerId(currentUser.getId());

        return new ResponseEntity<>(workspaceService.create(request), HttpStatus.CREATED);
    }

    /** GET /api/workspaces/{id} — anyone authenticated can view (auth improvements later). */
    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(workspaceService.getById(id));
    }

    /** GET /api/workspaces/me — list workspaces owned by the current user. */
    @GetMapping("/me")
    public ResponseEntity<List<WorkspaceResponse>> listMine(
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
        return ResponseEntity.ok(workspaceService.listByOwner(currentUser.getId()));
    }

    /** DELETE /api/workspaces/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        workspaceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}