package com.example.demo.comment.dto;

import com.example.demo.Member.Member;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.entity.ReplyComment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ReplyCommentResponseDTO {

    private Long id;
    private Member writer;

    private String content;

    public static List<ReplyCommentResponseDTO> toResponseEntity(List<ReplyComment> replyCommentList) {
        if (replyCommentList == null){
            return null;
        }

        List<ReplyCommentResponseDTO> responseDTOList = new ArrayList<>();

        for (ReplyComment reply: replyCommentList) {
            ReplyCommentResponseDTO response = ReplyCommentResponseDTO.builder()
                    .id(reply.getId())
                    .writer(reply.getWriter())
                    .content(reply.getContent())
                    .build();

            responseDTOList.add(response);
        }

        return responseDTOList;
    }
}
