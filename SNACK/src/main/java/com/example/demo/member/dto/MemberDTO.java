package com.example.demo.member.dto;

import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String loginId,pw,name,nickname;


    // LocalDate가 날짜만 저장!
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") //날짜 포멧 바꾸기
    private LocalDate birth;
    //private Date birth;



    public static MemberDTO toMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setLoginId(member.getLoginId());
        memberDTO.setPw(member.getPw());
        memberDTO.setName(member.getName());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setBirth(member.getBirth());


        return memberDTO;

    }


    public static MemberDTO toMemberDTO_after_login(Member member) {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setId(member.getId());
        memberDTO.setLoginId(member.getLoginId());
        memberDTO.setPw("");
        memberDTO.setName(member.getName());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setBirth(member.getBirth());

        return memberDTO;

    }

    public static MemberDTO toMemberDTO_with_LongId(Member member) {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setId(member.getId());
        memberDTO.setLoginId(member.getLoginId());
        memberDTO.setPw(member.getPw());
        memberDTO.setName(member.getName());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setBirth(member.getBirth());

        return memberDTO;

    }



}
