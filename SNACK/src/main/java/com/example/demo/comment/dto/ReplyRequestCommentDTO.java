package com.example.demo.comment.dto;

import com.example.demo.comment.entity.ReplyComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequestCommentDTO {
    private String content;
    private Long writerId;


    public static ReplyComment toEntity(ReplyRequestCommentDTO replyRequestDTO) {
        return ReplyComment.builder()
                .content(replyRequestDTO.getContent())
                .build();
    }
}
