package com.example.demo.profile.dto.profileUpdate;

import com.example.demo.profile.domain.user.User;
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

    public ProfileUpdateResponseDto(User user){
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
        this.introduction = user.getUserInfo().getIntroduction();
    }
}
