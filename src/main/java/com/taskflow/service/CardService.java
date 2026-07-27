package com.taskflow.service;

import com.taskflow.dto.request.CreateCardRequest;
import com.taskflow.dto.response.CardResponse;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.CardMapper;
import com.taskflow.model.BoardColumn;
import com.taskflow.model.Card;
import com.taskflow.model.User;
import com.taskflow.repository.CardRepository;
import com.taskflow.repository.ColumnRepository;
import com.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;
    private final UserRepository userRepository;
    private final OwnershipService ownershipService;

    @Transactional
    public CardResponse create(UUID columnId, CreateCardRequest request, UUID currentUserId) {
        ownershipService.checkBoardOwnership(columnId, currentUserId);
        BoardColumn column = columnRepository.findById(columnId)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found: " + columnId));

        // Optional assignee, resolve only if the client sent one
        User assignee = null;
        if (request.getAssigneeId() != null) {
            assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Assignee not found: " + request.getAssigneeId()));
        }

        // Determine next position
        long existingCount = cardRepository.countByColumn_Id(columnId);
        int nextPosition = (int) existingCount;

        Card card = Card.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .position(nextPosition)
                .dueDate(request.getDueDate())
                .column(column)
                .assignee(assignee) // nullable
                .build();

        return CardMapper.toResponse(cardRepository.save(card));
    }

    @Transactional(readOnly = true)
    public CardResponse getById(UUID id, UUID currentUserId) {
        ownershipService.checkBoardOwnership(id, currentUserId);
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found: " + id));
        return CardMapper.toResponse(card);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> listByColumn(UUID columnId, UUID currentUserId) {
        ownershipService.checkBoardOwnership(columnId, currentUserId);
        return cardRepository.findByColumn_IdOrderByPositionAsc(columnId).stream()
                .map(CardMapper::toResponse)
                .toList();
    }

    // Cards assigned to a specific user across all boards
    @Transactional(readOnly = true)
    public List<CardResponse> listByAssignee(UUID userId) {
        return cardRepository.findByAssignee_Id(userId).stream()
                .map(CardMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID id, UUID currentUserId) {
        ownershipService.checkBoardOwnership(id, currentUserId);
        cardRepository.deleteById(id);
    }
}