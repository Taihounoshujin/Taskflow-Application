package com.taskflow.controller;

import com.taskflow.dto.request.CreateBoardRequest;
import com.taskflow.dto.response.BoardResponse;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.BoardService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;

    private UUID getCurrentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"))
                .getId();
    }

    @PostMapping("/workspaces/{workspaceId}/boards")
    public ResponseEntity<BoardResponse> create(
            @PathVariable UUID workspaceId,
            @Valid @RequestBody CreateBoardRequest request,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        return new ResponseEntity<>(
                boardService.create(workspaceId, request, currentUserId),
                HttpStatus.CREATED);
    }

    @GetMapping("/workspaces/{workspaceId}/boards")
    public ResponseEntity<List<BoardResponse>> listByWorkspace(
            @PathVariable UUID workspaceId,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        return ResponseEntity.ok(boardService.listByWorkspace(workspaceId,  currentUserId));
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        return ResponseEntity.ok(boardService.getById(id, currentUserId));
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        boardService.delete(id, currentUserId);
        return ResponseEntity.noContent().build();
    }
}