package com.example.demo.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }



    // 생성 로직

    public Long saveBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    public Optional<Board> getBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }


    // 취약 부분 -> Board에 너무 관여하는 메서드 (set...)
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
