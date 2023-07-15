package com.example.demo.board;

import com.example.demo.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 생성 로직
    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseGet(Board::new);

    }


    public void updateBoard(Long boardId, Board updatedBoard) {
        Board existingBoard = boardRepository.findById(boardId).orElseGet(Board::new);

        existingBoard.setTitle(updatedBoard.getTitle());
        existingBoard.setGameTitle(updatedBoard.getGameTitle());
        existingBoard.setDate(updatedBoard.getDate());
        existingBoard.setNotice(updatedBoard.getNotice());

        // Save the updated board
        boardRepository.save(existingBoard);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
