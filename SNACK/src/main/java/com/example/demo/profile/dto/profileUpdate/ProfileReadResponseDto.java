package com.example.demo.profile.dto.profileUpdate;

import com.example.demo.entity.MemberEntity;
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

    public ProfileReadResponseDto(MemberEntity member){
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileimageurl();
        this.introduction = member.getUserInfo().getIntroduction();
    }

}
