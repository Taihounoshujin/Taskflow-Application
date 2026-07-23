package com.taskflow.controller;

import com.taskflow.dto.request.CreateColumnRequest;
import com.taskflow.dto.response.ColumnResponse;
import com.taskflow.service.ColumnService;
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
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<ColumnResponse> create(
            @PathVariable UUID boardId,
            @Valid @RequestBody CreateColumnRequest request) {
        return new ResponseEntity<>(columnService.create(boardId, request), HttpStatus.CREATED);
    }

    @GetMapping("/boards/{boardId}/columns")
    public ResponseEntity<List<ColumnResponse>> listByBoard(@PathVariable UUID boardId) {
        return ResponseEntity.ok(columnService.listByBoard(boardId));
    }

    @GetMapping("/columns/{id}")
    public ResponseEntity<ColumnResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(columnService.getById(id));
    }

    @DeleteMapping("/columns/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        columnService.delete(id);
        return ResponseEntity.noContent().build();
    }
}