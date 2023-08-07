package com.example.demo.board.dto;

import com.example.demo.Games;
import com.example.demo.board.entity.Board;
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

    private String writer;

    private String title;

    private Games gameTitle;

    private String etcTitle;

    private List<String> boardMembers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String notice;

    private Integer maxCount;

    private boolean autoCheckIn;

    public static BoardResponseDTO getBuild(Board board) {
        ArrayList<String> memberName = new ArrayList<>();

        board.getBoardMembers().iterator().forEachRemaining(
                boardMember -> memberName.add(boardMember.getMember().getNickname())
        );
        return BoardResponseDTO.builder()
                .id(board.getId())
                .writer(board.getWriter().getNickname())
                .title(board.getTitle())
                .gameTitle(board.getGameTitle())
                .etcTitle(board.getEtcTitle())
                .boardMembers(memberName)
                .date(board.getDate())
                .notice(board.getNotice())
                .maxCount(board.getMaxCount())
                .autoCheckIn(board.isAutoCheckIn())
                .build();
    }
}
