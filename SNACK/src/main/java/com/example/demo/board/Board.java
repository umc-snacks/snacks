package com.example.demo.board;


import com.example.demo.Member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//제목, 게임 종류, 게임 아이콘, 인원, 날자, 시간 공지사항, 참가 신청 옵션
public class Board {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "TITLE")
    @NotBlank(message = "제목은 공백이 될 수 없습니다.")
    private String title;

    @Column(name = "GAME_TITLE")
    @NotBlank
    private String gameTitle;

    @OneToMany(mappedBy = "board")
    private List<Member> members = new ArrayList<>();

    @Column(name = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Column(name = "NOTICE")
    private String notice;

    @Transient
    private int memberCount;


}
