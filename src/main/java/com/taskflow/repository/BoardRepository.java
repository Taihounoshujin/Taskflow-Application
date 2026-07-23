package com.taskflow.repository;

import com.taskflow.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// Repository for managing Board entities

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {
    // Find all boards in a given workspace
    List<Board> findByWorkspace_Id(UUID workspaceId);
    List<Board> findByWorkspace_Owner_Id(UUID userId);

    @Query("SELECT COUNT(b) FROM Board b WHERE b.workspace.id = :workspaceId")
    long countByWorkspaceId(@Param("workspaceId") UUID workspaceId);
}
