package com.example.demo.socialboard;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.comment.entity.Comment;
import com.example.demo.socialboard.dto.SocialBoardDTO;
import com.example.demo.socialboard.dto.VoteBoardDTO;
import com.example.demo.socialboard.entity.SocialBoard;
import com.example.demo.socialboard.entity.Vote;
import com.example.demo.socialboard.entity.VoteBoard;
import com.example.demo.socialboard.repository.SocialBoardRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                .comments(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();
        VoteBoardDTO board = (VoteBoardDTO) socialBoardDTO;

        // TODO vote등 (votes임) 다른 이름으로 넘어온 경우 알려주는 예외 처리 필요
        board.getVotes().iterator().forEachRemaining(
                vote -> {
                    vote.setVoteBoard(voteBoard);
                    voteRepository.save(vote);
//                     member.hasWriteArticle();
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


    public void deleteBoard(Long boardId) {
        socialBoardRepository.deleteById(boardId);
    }

    @Transactional
    public void updateBoard(Long boardId, SocialBoardDTO socialBoardDTO) {
        SocialBoard updatedBoard = socialBoardDTO.toEntity(null, null);
        SocialBoard existingBoard = socialBoardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + boardId));

        existingBoard.update(updatedBoard);

    }


}
