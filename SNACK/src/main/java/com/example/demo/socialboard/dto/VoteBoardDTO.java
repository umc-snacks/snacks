package com.example.demo.socialboard.dto;


import com.example.demo.socialboard.entity.Comment;
import com.example.demo.socialboard.entity.SocialBoard;
import com.example.demo.socialboard.entity.Vote;
import com.example.demo.socialboard.entity.VoteBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteBoardDTO extends SocialBoardDTO{

    private List<VoteDTO> votes = new ArrayList<>();

    @Override
    public SocialBoard toEntity() {
        List<CommentDTO> comments = this.getComments();
        List<Comment> commentEntities = comments.stream()
                .map(CommentDTO::toEntity)
                .collect(Collectors.toList());

        List<VoteDTO> votes = this.getVotes();
        List<Vote> voteEntities = votes.stream()
                .map(VoteDTO::toEntity)
                .collect(Collectors.toList());

        return VoteBoard.builder()
                .writer(this.getWriter())
                .content(this.getContent())
                .likes(this.getLikes())
                .comments(commentEntities)
                .votes(voteEntities)
                .build();
    }

}
