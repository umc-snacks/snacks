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
public class MemberResponseDTO {
    private Long id;
    private String loginId;
    private String pw;
    private String name;

    private String nickname;

    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") //날짜 포멧 바꾸기
    private LocalDate birth;

    private List<BoardMember> memberBoards;

    public static MemberResponseDTO toResponseEntity(Member member){
        return MemberResponseDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .memberBoards(member.getMemberBoards())
                .build();
    }
}
