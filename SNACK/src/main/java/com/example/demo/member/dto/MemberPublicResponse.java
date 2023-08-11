package com.example.demo.member.dto;

import com.example.demo.board.entity.BoardMember;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberPublicResponse {
    private Long id;
    private String nickname;

    public static MemberPublicResponse toResponseEntity(Member member){
        return MemberPublicResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
