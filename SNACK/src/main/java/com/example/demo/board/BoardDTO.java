package com.example.demo.board;

import com.example.demo.Games;
import com.example.demo.Member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class BoardDTO {

    @NotBlank(message = "제목은 공백이 될 수 없습니다.")
    private String title;

    @Enumerated(EnumType.STRING)
    private Games gameTitle;

    private String etcTitle;

    private List<Member> members = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    private String notice;

    private Integer maxCount;

    private int memberCount;

}