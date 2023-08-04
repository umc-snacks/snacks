package com.example.demo.profile.controller;

import com.example.demo.profile.domain.member.MemberRepository;
import com.example.demo.profile.dto.profileUpdate.ProfileReadResponseDto;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateRequestDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateResponseDto;
//import com.example.demo.profile.security.UserDetailsImpl;
import com.example.demo.profile.service.FollowService;
import com.example.demo.profile.service.MyInfoService;
import com.example.demo.profile.service.UserInfoService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final MyInfoService myInfoService;
    private final FollowService followService;
    private final UserInfoService userInfoService;
    private final MemberRepository memberRepository;

    //내 프로필 보기 테스트(성공!)
    @GetMapping("/myinfo1/{userId}")
    public MyInfoResponseDto readMyInfo(@PathVariable Long userId){
        return myInfoService.readMyInfo(memberRepository.findById(userId).get());
    }

    //팔로잉 테스트(성공!)
    @PostMapping("/follow/{userId}/{userId2}")
    public Boolean followUser(@PathVariable Long userId, @PathVariable Long userId2){
        return followService.followUser(userId, memberRepository.findById(userId2).get());
    }

    //남이 내 프로필 봤을 때 (성공!)
    @GetMapping("/myinfo/{userId}")
    public MyInfoResponseDto readUserInfo(@PathVariable Long userId){
        return userInfoService.readUserInfo(userId);
    }

    //유저 프로필 편집 페이지(닉네임, 프로필사진, 소개글)테스트(성공!!)
    @GetMapping("/user/{userId}")
    public ProfileReadResponseDto readProfile(@PathVariable Long userId) {
        return myInfoService.readProfile(memberRepository.findById(userId).get());
    }

    //유저 프로필 편집 테스트(성공!!)
    @PutMapping("/user/{userId}")
    public ProfileUpdateResponseDto updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto, @PathVariable Long userId){
        return myInfoService.updateProfile(profileUpdateRequestDto, memberRepository.findById(userId).get());
    }
}
