package com.taskflow.repository;

import com.taskflow.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Repository for managing Card entities

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    // Get all cards in a column, sorted by position
    List<Card> findByColumn_IdOrderByPositionAsc(UUID columnId);

    // Find all cards assigned to a specific user
    List<Card> findByAssignee_Id(UUID userId);

    // Find all cards due before a given timestamp
    List<Card> findByDueDateBefore(LocalDateTime date);

    //find overdue cards within a specific board
    @Query("SELECT c FROM Card c " +
            "WHERE c.column.board.id = :boardId " +
            "AND c.dueDate < :now")
    List<Card> findOverdueCardsByBoard(@Param("boardId") UUID boardId,
                                       @Param("now") LocalDateTime now);

    // Custom JPQL: find all cards tagged with a specific label
    @Query("SELECT c FROM Card c JOIN c.labels l WHERE l.id = :labelId")
    List<Card> findByLabelId(@Param("labelId") UUID labelId);

    // Count cards in a column
    long countByColumn_Id(UUID columnId);
}
