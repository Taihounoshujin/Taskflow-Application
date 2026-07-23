package com.taskflow.controller;

import com.taskflow.dto.request.CreateCardRequest;
import com.taskflow.dto.response.CardResponse;
import com.taskflow.model.User;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.CardService;
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
public class CardController {

    private final CardService cardService;
    private final UserRepository userRepository;

    @PostMapping("/columns/{columnId}/cards")
    public ResponseEntity<CardResponse> create(
            @PathVariable UUID columnId,
            @Valid @RequestBody CreateCardRequest request) {
        return new ResponseEntity<>(cardService.create(columnId, request), HttpStatus.CREATED);
    }

    @GetMapping("/columns/{columnId}/cards")
    public ResponseEntity<List<CardResponse>> listByColumn(@PathVariable UUID columnId) {
        return ResponseEntity.ok(cardService.listByColumn(columnId));
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<CardResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cardService.getById(id));
    }

    @GetMapping("/cards/me")
    public ResponseEntity<List<CardResponse>> listMine(
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
        return ResponseEntity.ok(cardService.listByAssignee(currentUser.getId()));
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}