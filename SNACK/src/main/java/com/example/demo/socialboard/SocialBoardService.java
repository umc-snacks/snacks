package com.example.demo.socialboard;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.socialboard.dto.SocialBoardDTO;
import com.example.demo.socialboard.dto.VoteBoardDTO;
import com.example.demo.socialboard.entity.SocialBoard;
import com.example.demo.socialboard.entity.VoteBoard;
import com.example.demo.socialboard.repository.SocialBoardRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SocialBoardService {

    private final SocialBoardRepository socialBoardRepository;
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public SocialBoardService(SocialBoardRepository socialBoardRepository, VoteRepository voteRepository, MemberRepository memberRepository) {
        this.socialBoardRepository = socialBoardRepository;
        this.voteRepository = voteRepository;
        this.memberRepository = memberRepository;
    }


    public SocialBoard saveBoard(@Valid SocialBoardDTO socialBoardDTO) {
        Member member = memberRepository.findById(socialBoardDTO.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + socialBoardDTO.getWriterId()));


        VoteBoard voteBoard = VoteBoard.builder()
                .writer(member)
                .content(socialBoardDTO.getContent())
                .likes(0L)
                .build();
        VoteBoardDTO board = (VoteBoardDTO) socialBoardDTO;

        // TODO vote등 (votes임) 다른 이름으로 넘어온 경우 알려주는 예외 처리 필요
        board.getVotes().iterator().forEachRemaining(
                vote -> {
                    vote.setVoteBoard(voteBoard);
                    voteRepository.save(vote);
                }
        );

        return voteBoard;
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
