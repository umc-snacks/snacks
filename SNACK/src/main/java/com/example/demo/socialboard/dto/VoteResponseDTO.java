package com.example.demo.socialboard.dto;


import com.example.demo.socialboard.entity.Vote;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteResponseDTO {
    private String voteElement;

    private Integer voteNum;

    public static List<VoteResponseDTO> toListEntity(List<Vote> votes) {
        ArrayList<VoteResponseDTO> responseDTOs = new ArrayList<>();

        votes.iterator().forEachRemaining(
                vote -> responseDTOs.add(toResponseEntity(vote))
        );

        return responseDTOs;
    }

    public static VoteResponseDTO toResponseEntity(Vote vote) {
        return VoteResponseDTO.builder()
                .voteElement(vote.getVoteElement())
                .voteNum(vote.getVoteNum())
                .build();
    }
}
