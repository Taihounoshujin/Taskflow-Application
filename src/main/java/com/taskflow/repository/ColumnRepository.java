package com.taskflow.repository;

import com.taskflow.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for managing BoardColumn entities.
 * <p>
 * Note: the entity is named BoardColumn (not Column) because "Column"
 * is a reserved keyword in SQL and would collide with @Column annotations.
 * <p>
 * Columns have a "position" field for drag-and-drop ordering within a board.
 */
@Repository
public interface ColumnRepository extends JpaRepository<BoardColumn, UUID> {

    /**
     * Get all columns for a board, sorted by their position field.
     * This is what your API returns when rendering a board left-to-right.
     *
     * @param boardId the board UUID
     * @return columns in display order
     */
    List<BoardColumn> findByBoard_IdOrderByPositionAsc(UUID boardId);

    /**
     * Find the highest position value in a board.
     * Used when adding a new column to place it at the end.
     */
    BoardColumn findFirstByBoard_IdOrderByPositionDesc(UUID boardId);
}