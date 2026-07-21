package com.taskflow.repository;

import com.taskflow.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Workspace entities.
 * Provides CRUD plus lookup by owner.
 */
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    /**
     * Find all workspaces owned by a specific user.
     * Powers the "list my workspaces" endpoint.
     */
    List<Workspace> findByOwner_Id(UUID ownerId);
}