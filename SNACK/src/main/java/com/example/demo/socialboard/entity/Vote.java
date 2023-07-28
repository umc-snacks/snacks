package com.example.demo.socialboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "VOTE_ELEMENT")
    private String voteElement;

    @Column(name = "VOTE_NUM")
    private Integer voteNum;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VOTEBOARD_ID")
    private VoteBoard voteBoard;

}
