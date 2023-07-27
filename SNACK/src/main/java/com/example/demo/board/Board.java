package com.example.demo.board;

import com.example.demo.BaseTimeEntity;
import com.example.demo.Games;
import com.example.demo.Member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOARD_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member writer;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;


    @Column(name = "NOTICE")
    private String notice;

    @Column(name = "MAX_COUNT")
    private Integer maxCount;

    @Column(name = "AUTO_CHECK")
    private boolean autoCheckIn;

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
                .memberCount(memberLen)
                .autoCheckIn(boardDTO.isAutoCheckIn())
                .build();

    }
}
