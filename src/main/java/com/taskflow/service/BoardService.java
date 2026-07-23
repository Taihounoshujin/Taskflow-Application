package com.taskflow.service;

import com.taskflow.dto.request.CreateBoardRequest;
import com.taskflow.dto.response.BoardResponse;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.BoardMapper;
import com.taskflow.model.Board;
import com.taskflow.model.Workspace;
import com.taskflow.repository.BoardRepository;
import com.taskflow.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public BoardResponse create(UUID workspaceId, CreateBoardRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found: " + workspaceId));

        Board board = Board.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspace(workspace)
                .build();

        return BoardMapper.toResponse(boardRepository.save(board));
    }

    @Transactional(readOnly = true)
    public BoardResponse getById(UUID id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found: " + id));
        return BoardMapper.toResponse(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> listByWorkspace(UUID workspaceId) {
        return boardRepository.findByWorkspace_Id(workspaceId).stream()
                .map(BoardMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID id) {
        if (!boardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Board not found: " + id);
        }
        boardRepository.deleteById(id);
    }
}