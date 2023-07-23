package com.example.demo.profile.service;

import com.example.demo.profile.domain.user.User;
import com.example.demo.profile.domain.user.UserRepository;
import com.example.demo.profile.domain.userinfo.UserInfoRepository;
import com.example.demo.profile.dto.profileUpdate.ProfileReadResponseDto;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateRequestDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyInfoService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    public MyInfoResponseDto readMyInfo(User user) {
        User tempUser = userRepository.findById(user.getId()).get();


        /*
        게시글 가져오는 코드
         */

        return MyInfoResponseDto.builder()
                .myProfileImageUrl(tempUser.getProfileImageUrl())
                .nickname(tempUser.getNickname())
                .articleCount(tempUser.getUserInfo().getArticleCount())
                .followerCount(tempUser.getUserInfo().getFollowerCount())
                .followCount(tempUser.getUserInfo().getFollowCount())
                .introduction(tempUser.getUserInfo().getIntroduction())
                .build();
    }

    public ProfileReadResponseDto readProfile(User user) {
        User tempUser = userRepository.findById(user.getId()).get();
        return new ProfileReadResponseDto(tempUser);
    }

    @Transactional
    public ProfileUpdateResponseDto updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto, User user) {
        User tempUser = userRepository.findById(user.getId()).get();
        tempUser.setNickname(profileUpdateRequestDto.getNickname());
        tempUser.setProfileImageUrl(profileUpdateRequestDto.getProfileImageUrl());
        tempUser.getUserInfo().setIntroduction(profileUpdateRequestDto.getInroduction());
        userRepository.save(tempUser);
        userInfoRepository.save(tempUser.getUserInfo());
        return new ProfileUpdateResponseDto(tempUser);
    }
}

