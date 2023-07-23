package com.example.demo.profile.controller;

import com.example.demo.profile.dto.profileUpdate.ProfileReadResponseDto;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateRequestDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateResponseDto;
import com.example.demo.profile.security.UserDetailsImpl;
import com.example.demo.profile.service.FollowService;
import com.example.demo.profile.service.MyInfoService;
import com.example.demo.profile.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyInfoController {

    private final MyInfoService myInfoService;
    private final FollowService followService;
    private final UserInfoService userInfoService;

    //내 프로필 보기
    @GetMapping("/myinfo")
    public MyInfoResponseDto readMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return myInfoService.readMyInfo(userDetails.getUser());
    }

    //팔로잉
    @PostMapping("/follow/{userId}")
    public Boolean followUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.followUser(userId, userDetails.getUser());
    }

    //남이 내 프로필 봤을 때
    @GetMapping("/myinfo/{userId}")
    public MyInfoResponseDto readUserInfo(@PathVariable Long userId){
        return userInfoService.readUserInfo(userId);
    }

    //유저 프로필 편집 페이지(닉네임, 프로필사진, 소개글)//
    @GetMapping("/user")
    public ProfileReadResponseDto readProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myInfoService.readProfile(userDetails.getUser());
    }

    //유저 프로필 편집
    @PutMapping("/user")
    public ProfileUpdateResponseDto updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return myInfoService.updateProfile(profileUpdateRequestDto, userDetails.getUser());
    }
}
