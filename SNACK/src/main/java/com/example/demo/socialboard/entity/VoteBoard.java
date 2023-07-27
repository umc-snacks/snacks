package com.example.demo.socialboard.entity;

import com.example.demo.socialboard.dto.SocialBoardDTO;
import com.example.demo.socialboard.dto.VoteBoardDTO;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("V")
@SuperBuilder
@NoArgsConstructor
public class VoteBoard extends SocialBoard {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOCIAL_BOARD_ID")
    private List<Vote> votes = new ArrayList<>();
}