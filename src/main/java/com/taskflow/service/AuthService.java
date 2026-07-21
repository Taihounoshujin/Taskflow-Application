package com.taskflow.service;

import com.taskflow.dto.request.LoginRequest;
import com.taskflow.dto.response.AuthResponse;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.User;
import com.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Handles login: verifies credentials, issues JWT.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Authenticate credentials and return a signed JWT.
     * <p>
     * authenticationManager.authenticate() calls CustomUserDetailsService
     * to load the user and verifies the password against the BCrypt hash.
     * If credentials are wrong, it throws BadCredentialsException (401).
     */
    public AuthResponse login(LoginRequest request) {
        // Verify email + password. Throws on failure.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        // If we got here, credentials are valid. Load user for the response body.
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found: " + request.getEmail()));

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .build();
    }
}