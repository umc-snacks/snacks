package com.example.demo.board.enrollment;

import com.example.demo.board.entity.Board;
import com.example.demo.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ENROLL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REQUEST_MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "REQUEST_BOARD_ID")
    private Board board;


}
