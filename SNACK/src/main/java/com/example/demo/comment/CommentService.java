package com.example.demo.comment;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.comment.dto.CommentRequestDTO;
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
    public Comment insert(Long boardId, CommentRequestDTO commentRequestDTO) {
        Member member = memberRepository.findById(commentRequestDTO.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + commentRequestDTO.getWriterId()));

        SocialBoard board = socialBoardRepository.findById(boardId)
                        .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + boardId));

        Comment comment = Comment.builder()
                .board(board)
                .writer(member)
                .content(commentRequestDTO.getContent())
                .build();

        return commentRepository.save(comment);
    }


    public Comment update(Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Could not found comment Id : " + commentId));

        Comment updatedComment = CommentRequestDTO.toEntity(commentRequestDTO);

        existingComment.setContent(updatedComment.getContent());
        return existingComment;
    }
}
