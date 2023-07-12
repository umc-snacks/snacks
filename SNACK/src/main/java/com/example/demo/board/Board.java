package com.example.demo.board;


import com.example.demo.Member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
//제목, 게임 종류, 게임 아이콘, 인원, 날자, 시간 공지사항, 참가 신청 옵션
public class Board {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "GAME_TITLE")
    private String gameTitle;

    @OneToMany(mappedBy = "board")
    private List<Member> members = new ArrayList<>();

    @Column(name = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Column(name = "NOTICE")
    private String notice;

    private int memberCount;



}
