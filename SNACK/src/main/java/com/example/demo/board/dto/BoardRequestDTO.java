package com.example.demo.board.dto;

import com.example.demo.Games;
import com.example.demo.Member.Member;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class BoardRequestDTO {

    @NotBlank(message = "제목은 공백이 될 수 없습니다.")
    @Length(min = 1, max = 1000, message = "제목의 길이는 1이상 1000이하여야 합니다.")
    private String title;

    @Enumerated(EnumType.STRING)
    private Games gameTitle;

    private String etcTitle;

    @NotNull(message = "작성자의 Id가 필요합니다")
    private Long writerId;

    @NotNull
    private List<Long> memberIds = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Future(message = "모집시간은 현재시간보다 미래여야합니다.")  // 미래의 시간이어야 댐
    private LocalDateTime date;

    private String notice;

    @DecimalMin(value = "1", message = "1명 이상의 이용자가 있어야합니다.")
    @DecimalMax(value = "100", message = "100이하의 사용자만 게시판을 이용할 수 있습니다.")
    private Integer maxCount;

    private int memberCount;

    private boolean autoCheckIn;

    public static Board toEntity(BoardRequestDTO boardRequestDTO, List<BoardMember> members, Member writer) {
        return Board.builder()
                .title(boardRequestDTO.getTitle())
                .gameTitle(boardRequestDTO.getGameTitle())
                .writer(writer)
                .boardMembers(members)
                .date(boardRequestDTO.getDate())
                .notice(boardRequestDTO.getNotice())
                .maxCount(boardRequestDTO.getMaxCount())
                .memberCount(members.size())
                .autoCheckIn(boardRequestDTO.isAutoCheckIn())
                .build();
    }

}