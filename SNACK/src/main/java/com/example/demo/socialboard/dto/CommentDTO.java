package com.example.demo.socialboard.dto;

import com.example.demo.Member.Member;
import com.example.demo.socialboard.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String content;
    private Member writer;

    public Comment toEntity(){
        return Comment.builder()
                .content(this.getContent())
                .writer(this.getWriter())
                .build();
    }
}
