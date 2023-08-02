package com.example.demo.board.dto;

import com.example.demo.Games;
import com.example.demo.Member.Member;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
public class BoardResponseDTO {
    private Long id;

    private Member writer;

    private String title;

    private Games gameTitle;

    private String etcTitle;

    private List<BoardMember> boardMembers = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String notice;

    private Integer maxCount;

    private boolean autoCheckIn;

    public static BoardResponseDTO getBuild(Board board) {
        List<BoardMember> members = board.getBoardMembers();
        return BoardResponseDTO.builder()
                .id(board.getId())
                .writer(board.getWriter())
                .title(board.getTitle())
                .gameTitle(board.getGameTitle())
                .etcTitle(board.getEtcTitle())
                .boardMembers(members)
                .date(board.getDate())
                .notice(board.getNotice())
                .maxCount(board.getMaxCount())
                .autoCheckIn(board.isAutoCheckIn())
                .build();
    }
}
