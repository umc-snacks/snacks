package com.example.demo.board.entity;

import com.example.demo.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_login_id")
    private Member member;

}
