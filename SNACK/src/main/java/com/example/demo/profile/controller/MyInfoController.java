package com.example.demo.profile.controller;

import com.example.demo.profile.domain.userinfo.UserInfo;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
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
    public MyInfoResponseDto readMyInfo(/*로그인한 정보를 어떻게 받아오지?*/){
        return myInfoService.readMyInfo(/*유저정보*/);
    }

    //팔로잉
    @PostMapping("/follow/{userId}")
    public Boolean followUser(@PathVariable Long userId, /*로그인한 유저 정보 받아오기*/){
        return followService.followUser(userId, /*로그인 유저*/);
    }

    //남이 내 프로필 봤을 때
    @GetMapping("/myinfo/{userId}")
    public MyInfoResponseDto readUserInfo(@PathVariable Long userId){
        return userInfoService.readUserInfo(userId);
    }

    //유저 프로필 편집(닉네임, 프로필사진, 소개글)//

}
