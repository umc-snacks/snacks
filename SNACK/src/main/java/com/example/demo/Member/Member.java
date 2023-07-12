package com.example.demo.Member;


import com.example.demo.board.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String nickname;

    private String email;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
