package com.taskflow.mapper;

import com.taskflow.dto.response.CardResponse;
import com.taskflow.model.Card;
import com.taskflow.model.Label;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class CardMapper {

    private CardMapper() {}

    public static CardResponse toResponse(Card card) {
        if (card == null) return null;

        Set<UUID> labelIds = card.getLabels().stream()
                .map(Label::getId)
                .collect(Collectors.toSet());

        return CardResponse.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .position(card.getPosition())
                .dueDate(card.getDueDate())
                .columnId(card.getColumn().getId())
                .assigneeId(card.getAssignee() != null ? card.getAssignee().getId() : null)
                .labelIds(labelIds)
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .build();
    }
}