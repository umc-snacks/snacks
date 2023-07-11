package com.example.demo.Member;


import com.example.demo.board.Board;
import jakarta.persistence.*;

@Entity
public class MemberDTO {
    @GeneratedValue
    @Id
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String nick_name;

    private String email;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
