package com.example.demo.board;


import com.example.demo.Games;
import com.example.demo.Member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardSearch {
    private String title;
    private Games gameTitle;
    private String etcTitle;
    private List<Member> members = new ArrayList<>();
    private LocalDateTime date;
    private String notice;
    private Integer maxCount;
    private int memberCount;


}
