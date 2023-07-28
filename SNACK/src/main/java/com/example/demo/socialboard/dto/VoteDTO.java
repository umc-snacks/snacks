package com.example.demo.socialboard.dto;

import com.example.demo.socialboard.entity.Comment;
import com.example.demo.socialboard.entity.Vote;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {

    @NotBlank
    private String voteElement;

    private Integer voteNum;

    public Vote toEntity(){
        return Vote.builder()
                .voteElement(this.getVoteElement())
                .voteNum(this.voteNum)
                .build();
    }
}
