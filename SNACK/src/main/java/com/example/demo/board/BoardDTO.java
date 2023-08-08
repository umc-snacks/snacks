package com.example.demo.board;

import com.example.demo.Games;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO{
    @Enumerated(EnumType.STRING)
    private Games gameTitle;

    private String Title;

    private String etcTitle;

    private List<Member> members = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String notice;

    private Integer maxCount;

    private int memberCount;

    private boolean autoCheckIn;

}