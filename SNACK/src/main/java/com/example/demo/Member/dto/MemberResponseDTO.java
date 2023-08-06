package com.example.demo.Member.dto;

import com.example.demo.Member.Member;
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

    public static MemberResponseDTO toResponseEntity(Member member){
        return MemberResponseDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .memberBoards(member.getMemberBoards())
                .build();
    }

}
