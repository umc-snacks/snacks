package com.example.demo.profile.dto.profileUpdate;

import com.example.demo.profile.domain.member.Member;
import lombok.Getter;

@Getter
public class ProfileReadResponseDto {
    private final String nickname;
    private final String profileImageUrl;
    private final String introduction;

    public ProfileReadResponseDto( String nickname, String profileImageUrl, String introduction){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    public ProfileReadResponseDto(Member member){
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();
        this.introduction = member.getUserInfo().getIntroduction();
    }

}
