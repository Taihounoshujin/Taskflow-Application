package com.taskflow.mapper;

import com.taskflow.dto.response.UserResponse;
import com.taskflow.model.User;

// Converts User entities to DTOs
public final class UserMapper {

    private UserMapper() {} // utility class

    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
