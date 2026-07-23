package com.taskflow.controller;

import com.taskflow.dto.request.CreateBoardRequest;
import com.taskflow.dto.response.BoardResponse;
import com.taskflow.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/workspaces/{workspaceId}/boards")
    public ResponseEntity<BoardResponse> create(
            @PathVariable UUID workspaceId,
            @Valid @RequestBody CreateBoardRequest request) {
        return new ResponseEntity<>(boardService.create(workspaceId, request), HttpStatus.CREATED);
    }

    @GetMapping("/workspaces/{workspaceId}/boards")
    public ResponseEntity<List<BoardResponse>> listByWorkspace(@PathVariable UUID workspaceId) {
        return ResponseEntity.ok(boardService.listByWorkspace(workspaceId));
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(boardService.getById(id));
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}