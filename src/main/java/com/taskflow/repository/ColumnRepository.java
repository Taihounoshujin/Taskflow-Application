package com.taskflow.repository;

import com.taskflow.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// Repository for managing BoardColumn entities

@Repository
public interface ColumnRepository extends JpaRepository<BoardColumn, UUID> {

    // Get all columns for a board, sorted by their position field
    List<BoardColumn> findByBoard_IdOrderByPositionAsc(UUID boardId);

    // Find the highest position value in a board
    BoardColumn findFirstByBoard_IdOrderByPositionDesc(UUID boardId);
}