package com.taskflow.service;

import com.taskflow.dto.request.CreateColumnRequest;
import com.taskflow.dto.response.ColumnResponse;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.ColumnMapper;
import com.taskflow.model.Board;
import com.taskflow.model.BoardColumn;
import com.taskflow.repository.BoardRepository;
import com.taskflow.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;

    // Create a column and place it at the end of the board
    @Transactional
    public ColumnResponse create(UUID boardId, CreateColumnRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found: " + boardId));

        BoardColumn lastColumn = columnRepository.findFirstByBoard_IdOrderByPositionDesc(boardId);
        int nextPosition = (lastColumn == null) ? 0 : lastColumn.getPosition() + 1;

        BoardColumn column = BoardColumn.builder()
                .name(request.getName())
                .position(nextPosition)
                .board(board)
                .build();

        return ColumnMapper.toResponse(columnRepository.save(column));
    }

    @Transactional(readOnly = true)
    public ColumnResponse getById(UUID id) {
        BoardColumn column = columnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Column not found: " + id));
        return ColumnMapper.toResponse(column);
    }

    @Transactional(readOnly = true)
    public List<ColumnResponse> listByBoard(UUID boardId) {
        return columnRepository.findByBoard_IdOrderByPositionAsc(boardId).stream()
                .map(ColumnMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID id) {
        if (!columnRepository.existsById(id)) {
            throw new ResourceNotFoundException("Column not found: " + id);
        }
        columnRepository.deleteById(id);
    }
}