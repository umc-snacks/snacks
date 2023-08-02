package com.example.demo.socialboard.dto;

import com.example.demo.comment.dto.CommentResponseDTO;
import com.example.demo.socialboard.entity.SocialBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder

public abstract class SocialBoardResponseDTO {
    private String writer;

    private String content;

    private Long likes = 0L;

    private List<CommentResponseDTO> comments;

    public abstract SocialBoardResponseDTO toResponseEntity(SocialBoard socialBoard);
}
