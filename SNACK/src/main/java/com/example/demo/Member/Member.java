package com.example.demo.Member;


import com.example.demo.board.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "BOARD_ID")
    private Board board;


    /**
     * 연관관계 메서드
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
        board.getMembers().add(this);
    }
}
