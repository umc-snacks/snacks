package com.example.demo.comment.dto;

import com.example.demo.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {
    private String content;
    private Long writerId;


    public static Comment toEntity(CommentRequestDTO commentRequestDTO){
        return Comment.builder()
                .content(commentRequestDTO.getContent())
                .build();
    }
}
