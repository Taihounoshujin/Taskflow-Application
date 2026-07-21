package com.taskflow.repository;

import com.taskflow.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for managing Board entities.
 * <p>
 * Boards belong to a Workspace, which is owned by a User.
 * Spring Data JPA generates queries from method names — the underscore
 * navigates relationships (workspace.owner.id).
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    /**
     * Find all boards in a given workspace.
     *
     * @param workspaceId the workspace UUID
     * @return list of boards (empty if none found)
     */
    List<Board> findByWorkspace_Id(UUID workspaceId);

    /**
     * Find all boards owned (indirectly) by a specific user,
     * by traversing workspace -> owner -> id.
     *
     * @param userId the owner's UUID
     * @return list of boards across all the user's workspaces
     */
    List<Board> findByWorkspace_Owner_Id(UUID userId);

    /**
     * Custom JPQL example: count boards in a workspace.
     * Useful for dashboards or enforcing tier limits.
     */
    @Query("SELECT COUNT(b) FROM Board b WHERE b.workspace.id = :workspaceId")
    long countByWorkspaceId(@Param("workspaceId") UUID workspaceId);
}
