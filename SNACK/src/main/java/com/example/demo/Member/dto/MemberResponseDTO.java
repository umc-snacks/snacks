package com.example.demo.Member.dto;

import com.example.demo.board.entity.BoardMember;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDTO {
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private List<BoardMember> memberBoards;

}
