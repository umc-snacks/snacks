package com.example.demo.profile.dto.profileUpdate;

import com.example.demo.entity.Member;
import lombok.Getter;

@Getter
public class ProfileUpdateResponseDto {
    private final String nickname;
    private final String profileImageUrl;
    private final String introduction;

    public ProfileUpdateResponseDto(String nickname, String profileImageUrl, String introduction){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    public ProfileUpdateResponseDto(Member member){
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileimageurl();
        this.introduction = member.getUserInfo().getIntroduction();
    }
}
