package com.example.demo.member.dto;

import com.example.demo.member.entity.Member;
import lombok.*;

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
