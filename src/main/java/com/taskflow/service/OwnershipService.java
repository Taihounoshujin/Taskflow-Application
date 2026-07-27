package com.taskflow.service;

import com.taskflow.exception.AccessDeniedException;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.*;
import com.taskflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Centralized ownership checks. Every service delegates to these methods
 * before performing writes on user data.
 * <p>
 * All checks throw AccessDeniedException (403) on failure, or
 * ResourceNotFoundException (404) if the resource doesn't exist at all.
 */
@Service
@RequiredArgsConstructor
public class OwnershipService {

    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final CardRepository cardRepository;

    /** Verify the user owns the given workspace. */
    @Transactional(readOnly = true)
    public void checkWorkspaceOwnership(UUID workspaceId, UUID userId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found: " + workspaceId));
        if (!workspace.getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have access to this workspace");
        }
    }

    /** Verify the user owns the workspace containing this board. */
    @Transactional(readOnly = true)
    public void checkBoardOwnership(UUID boardId, UUID userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found: " + boardId));
        if (!board.getWorkspace().getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have access to this board");
        }
    }

    /** Verify the user owns the workspace containing this column's board. */
    @Transactional(readOnly = true)
    public void checkColumnOwnership(UUID columnId, UUID userId) {
        BoardColumn column = columnRepository.findById(columnId)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found: " + columnId));
        if (!column.getBoard().getWorkspace().getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have access to this column");
        }
    }

    /** Verify the user owns the workspace containing this card's column's board. */
    @Transactional(readOnly = true)
    public void checkCardOwnership(UUID cardId, UUID userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found: " + cardId));
        if (!card.getColumn().getBoard().getWorkspace().getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have access to this card");
        }
    }
}