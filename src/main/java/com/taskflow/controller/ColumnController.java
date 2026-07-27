package com.taskflow.controller;

import com.taskflow.dto.request.CreateColumnRequest;
import com.taskflow.dto.response.ColumnResponse;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.ColumnService;
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
public class ColumnController {

    private final ColumnService columnService;
    private final UserRepository userRepository;

    private UUID getCurrentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"))
                .getId();
    }

    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<ColumnResponse> create(
            @PathVariable UUID boardId,
            @Valid @RequestBody CreateColumnRequest request,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);

        return new ResponseEntity<>(columnService.create(boardId, request, currentUserId), HttpStatus.CREATED);
    }

    @GetMapping("/boards/{boardId}/columns")
    public ResponseEntity<List<ColumnResponse>> listByBoard(
            @PathVariable UUID boardId,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        return ResponseEntity.ok(columnService.listByBoard(boardId, currentUserId));
    }

    @GetMapping("/columns/{id}")
    public ResponseEntity<ColumnResponse> getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        return ResponseEntity.ok(columnService.getById(id, currentUserId));
    }

    @DeleteMapping("/columns/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal) {
        UUID currentUserId = getCurrentUserId(principal);
        columnService.delete(id, currentUserId);
        return ResponseEntity.noContent().build();
    }
}