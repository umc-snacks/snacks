package com.example.demo.comment;


import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.comment.dto.ReplyRequestCommentDTO;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.entity.ReplyComment;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReplyCommentService {
    private final ReplyCommentRepository replyCommentRepository;
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    public ReplyCommentService(ReplyCommentRepository replyCommentRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        this.replyCommentRepository = replyCommentRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    public ReplyComment insert(Long commentId, ReplyRequestCommentDTO replyRequestCommentDTO){
        Member member = memberRepository.findById(replyRequestCommentDTO.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + replyRequestCommentDTO.getWriterId()));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Could not found comment id : " + commentId));

        ReplyComment replyComment = ReplyComment.builder()
                .comment(comment)
                .writer(member)
                .content(replyRequestCommentDTO.getContent())
                .build();

        return replyCommentRepository.save(replyComment);
    }
}
