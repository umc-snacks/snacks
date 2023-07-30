package com.example.demo.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Games;
import com.example.demo.profile.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardDTO {

    @NotBlank(message = "제목은 공백이 될 수 없습니다.")
    private String title;

    @Enumerated(EnumType.STRING)
    private Games gameTitle;

    private String etcTitle;

    private List<Member> members = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String notice;

    private Integer maxCount;

    private int memberCount;

    private boolean autoCheckIn;

}