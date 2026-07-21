package com.taskflow.repository;

import com.taskflow.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository for managing Card entities (individual tasks).
 * <p>
 * Cards are the primary user-facing object — they belong to a Column,
 * can be assigned to a User, can have multiple Labels, and may have a due date.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    /**
     * Get all cards in a column, sorted by position (top to bottom).
     */
    List<Card> findByColumn_IdOrderByPositionAsc(UUID columnId);

    /**
     * Find all cards assigned to a specific user — across all boards.
     * Powers a "My Tasks" view.
     */
    List<Card> findByAssignee_Id(UUID userId);

    /**
     * Find all cards due before a given timestamp.
     * Use LocalDateTime.now() to get currently overdue cards.
     */
    List<Card> findByDueDateBefore(LocalDateTime date);

    /**
     * Custom JPQL: find overdue cards within a specific board.
     * Demonstrates multi-condition queries that traverse relationships
     * (card -> column -> board).
     */
    @Query("SELECT c FROM Card c " +
            "WHERE c.column.board.id = :boardId " +
            "AND c.dueDate < :now")
    List<Card> findOverdueCardsByBoard(@Param("boardId") UUID boardId,
                                       @Param("now") LocalDateTime now);

    /**
     * Custom JPQL: find all cards tagged with a specific label.
     * Uses JOIN to traverse the many-to-many relationship.
     */
    @Query("SELECT c FROM Card c JOIN c.labels l WHERE l.id = :labelId")
    List<Card> findByLabelId(@Param("labelId") UUID labelId);

    /**
     * Count cards in a column. Useful for analytics / "WIP limit" enforcement.
     */
    long countByColumn_Id(UUID columnId);
}
