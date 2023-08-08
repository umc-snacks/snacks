package com.example.demo.socialboard.dto;


import com.example.demo.comment.entity.Comment;
import com.example.demo.member.entity.Member;
import com.example.demo.socialboard.entity.SocialBoard;
import com.example.demo.socialboard.entity.Vote;
import com.example.demo.socialboard.entity.VoteBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VoteBoardDTO extends SocialBoardDTO{

    private List<Vote> votes = new ArrayList<>();


    @Override
    public SocialBoard toEntity(Member writer, List<Comment> comments) {

        return  VoteBoard.builder()
                .writer(writer)
                .content(this.getContent())
                .likes(this.getLikes())
                .comments(comments)
                .build();
    }
}
