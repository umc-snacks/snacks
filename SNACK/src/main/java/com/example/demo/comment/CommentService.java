package com.example.demo.comment;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.board.BoardRepository;
import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.socialboard.repository.SocialBoardRepository;
import com.example.demo.comment.entity.Comment;
import com.example.demo.socialboard.entity.SocialBoard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final SocialBoardRepository socialBoardRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, BoardRepository boardRepository, SocialBoardRepository socialBoardRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.socialBoardRepository = socialBoardRepository;
    }


    @Transactional
    public Comment insert(Long boardId, CommentDTO commentDTO) {
        Member member = memberRepository.findById(commentDTO.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + commentDTO.getWriterId()));

        SocialBoard board = socialBoardRepository.findById(boardId)
                        .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + boardId));

        Comment comment = Comment.builder()
                .board(board)
                .writer(member)
                .content(commentDTO.getContent())
                .build();

        return commentRepository.save(comment);
    }

}
