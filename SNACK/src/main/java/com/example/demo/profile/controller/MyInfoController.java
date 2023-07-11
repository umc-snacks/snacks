package com.example.demo.profile.controller;

import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import com.example.demo.profile.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyInfoController {

    private final MyInfoService myInfoService;


    //내 프로필 보기
    @GetMapping("/myinfo")
    public MyInfoResponseDto readMyInfo(/*로그인한 정보를 어떻게 받아오지?*/){
        return myInfoService.readMyInfo(/*유저정보*/);
    }

    //팔로잉

    //남이 내 프로필 봤을 때

    //유저 프로필 편집(닉네임, 프로필사진)//


}
