package com.example.demo.board;


import com.example.demo.Games;
import com.example.demo.Member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
//제목, 게임 종류, 게임 아이콘, 인원, 날자, 시간 공지사항, 참가 신청 옵션
public class Board {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "GAME_TITLE")
    @Enumerated(EnumType.STRING)
    private Games gameTitle;

    @Column(name = "ETC_TITLE")
    private String etcTitle;

    @OneToMany(mappedBy = "board")
    @JsonIgnoreProperties({"board"})
    private List<Member> members = new ArrayList<>();

    @Column(name = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Column(name = "NOTICE")
    private String notice;

    @Column(name = "MAX_COUNT")
    private Integer maxCount;

    @Transient
    private Integer memberCount;


    public static Optional<Board> createBoard(BoardDTO boardDTO) {
        Integer memberLen = boardDTO.getMembers().size();
        Integer maxCount = boardDTO.getMaxCount();

        return maxCount > memberLen ? Optional.of(buildBoard(boardDTO)) : Optional.empty();

    }

    private static Board buildBoard(BoardDTO boardDTO) {
        List<Member> members = boardDTO.getMembers();
        Integer memberLen = members.size();

        return Board.builder()
                .title(boardDTO.getTitle())
                .gameTitle(boardDTO.getGameTitle())
                .members(members)
                .date(boardDTO.getDate())
                .notice(boardDTO.getNotice())
                .maxCount(boardDTO.getMaxCount())
                .memberCount(memberLen).build();
    }

}
