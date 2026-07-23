package com.taskflow.service;

import com.taskflow.dto.request.CreateUserRequest;
import com.taskflow.dto.response.UserResponse;
import com.taskflow.exception.EmailAlreadyExistsException;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.UserMapper;
import com.taskflow.model.User;
import com.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

// Business logic for User operations.

@Service
@RequiredArgsConstructor  // Lombok: constructor injection for all final fields
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Register a new user
    @Transactional
    public UserResponse register(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already registered: " + request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .build();

        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }

    // Look up a user by ID
    @Transactional(readOnly = true)
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return UserMapper.toResponse(user);
    }
}