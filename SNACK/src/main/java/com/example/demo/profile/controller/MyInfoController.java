package com.example.demo.profile.controller;

import com.example.demo.member.repository.MemberRepository;
import com.example.demo.profile.dto.profileUpdate.ProfileReadResponseDto;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateRequestDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateResponseDto;
import com.example.demo.profile.service.FollowService;
import com.example.demo.profile.service.MyInfoService;
import com.example.demo.profile.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyInfoController {

    private final MyInfoService myInfoService;
    private final FollowService followService;
    private final UserInfoService userInfoService;
    private final MemberRepository memberRepository;

    //내 프로필 보기
    @GetMapping("/myinfo")
    public MyInfoResponseDto readMyInfo(Authentication authentication){
        return myInfoService.readMyInfo(memberRepository.findById(Long.valueOf(authentication.getName())).get());
    }

    //팔로잉
    @PostMapping("/follow/{userId}")
    public Boolean followUser(@PathVariable Long userId, Authentication authentication){
        return followService.followUser(userId, memberRepository.findById(Long.valueOf(authentication.getName())).get());
    }

    //남이 내 프로필 봤을 때
    @GetMapping("/myinfo/{userId}")
    public MyInfoResponseDto readUserInfo(@PathVariable Long userId){
        return userInfoService.readUserInfo(userId);
    }

    //유저 프로필 편집 페이지(닉네임, 프로필사진, 소개글)
    @GetMapping("/user")
    public ProfileReadResponseDto readProfile(Authentication authentication) {
        return myInfoService.readProfile(memberRepository.findById(Long.valueOf(authentication.getName())).get());
    }

    //유저 프로필 편집 테스트(성공!!)
    @PutMapping("/user")
    public ProfileUpdateResponseDto updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto, Authentication authentication){
        return myInfoService.updateProfile(profileUpdateRequestDto, memberRepository.findById(Long.valueOf(authentication.getName())).get());
    }
}
