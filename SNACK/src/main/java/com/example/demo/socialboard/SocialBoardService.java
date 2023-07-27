package com.example.demo.socialboard;

import com.example.demo.socialboard.entity.SocialBoard;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialBoardService {

    private final SocialBoardRepository socialBoardRepository;

    @Autowired
    public SocialBoardService(SocialBoardRepository socialBoardRepository) {
        this.socialBoardRepository = socialBoardRepository;
    }

    public Long saveBoard(@Valid SocialBoard socialBoard) {
        socialBoardRepository.save(socialBoard);

        return socialBoard.getId();
    }


    public Optional<SocialBoard> getBoard(Long boardId) {
        return socialBoardRepository.findById(boardId);
    }

    public List<SocialBoard> getBoards() {
        return socialBoardRepository.findAll();
    }


    // 취약 부분 -> Board에 너무 관여하는 메서드 (set...)
//    public void updateBoard(Long boardId, SocialBoard updatedBoard) {
//        SocialBoard existingBoard = socialBoardRepository.findById(boardId).orElseGet(SocialBoard::new);
//
//        existingBoard.setTitle(updatedBoard.getTitle());
//        existingBoard.setGameTitle(updatedBoard.getGameTitle());
//        existingBoard.setDate(updatedBoard.getDate());
//        existingBoard.setNotice(updatedBoard.getNotice());
//
//        // Save the updated board
//        socialBoardRepository.save(existingBoard);
//    }

    public void deleteBoard(Long boardId) {
        socialBoardRepository.deleteById(boardId);
    }

}
