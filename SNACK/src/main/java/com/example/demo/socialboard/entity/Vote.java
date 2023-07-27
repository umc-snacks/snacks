package com.example.demo.socialboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "VOTE_ELEMENT")
    private String voteElement;

    @Column(name = "VOTE_NUM")
    private Integer voteNum;



}
