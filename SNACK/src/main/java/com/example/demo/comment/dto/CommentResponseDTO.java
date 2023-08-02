package com.example.demo.comment.dto;

import com.example.demo.Member.Member;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.entity.ReplyComment;
import com.example.demo.socialboard.entity.SocialBoard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CommentResponseDTO {

    private Long id;

    private String content;

    private String writer;

    private List<ReplyCommentResponseDTO> replies;

    public static List<CommentResponseDTO> toListEntity(List<Comment> comments) {
        List<CommentResponseDTO> responseDTOList = new ArrayList<>();
        for (Comment comment: comments) {
            CommentResponseDTO dto = CommentResponseDTO.toResponseEntity(comment);
            responseDTOList.add(dto);
        }
        return responseDTOList;
    }

    public static CommentResponseDTO toResponseEntity(Comment comment){

        List<ReplyCommentResponseDTO> responseDTOList = ReplyCommentResponseDTO.toResponseEntity(comment.getReplies());
        String nickname = comment.getWriter().getNickname();

        return CommentResponseDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writer(nickname)
                .replies(responseDTOList)
                .build();
    }
}
