package com.example.demo.socialboard.entity;

import com.example.demo.socialboard.dto.SocialBoardDTO;
import com.example.demo.socialboard.dto.VoteBoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("V")
@SuperBuilder
@NoArgsConstructor
public class VoteBoard extends SocialBoard {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voteBoard")
    private List<Vote> votes = new ArrayList<>();

}