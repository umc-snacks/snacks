package com.example.demo.profile.dto.profileUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequestDto {
    private String nickname;

    private String profileImageUrl;

    private String introduction;
    public ProfileUpdateRequestDto(String nickname, String profileImageUrl, String introduction){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }
}
