package com.taskflow.controller;

import com.taskflow.dto.request.CreateUserRequest;
import com.taskflow.dto.response.UserResponse;
import com.taskflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST endpoints for user account operations.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * POST /api/users/register — create a new user account.
     * The @Valid annotation triggers Jakarta validation on CreateUserRequest.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest request) {
        UserResponse created = userService.register(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * GET /api/users/{id} — look up a user by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}