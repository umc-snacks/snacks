package com.example.demo.profile.service;

import com.example.demo.profile.domain.member.Member;
import com.example.demo.profile.domain.member.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final UserInfoRepository userInfoRepository;
    public MyInfoResponseDto readMyInfo(Member member) {
        Member tempMember = memberRepository.findById(member.getId()).get();


        /*
        게시글 가져오는 코드
         */

        return MyInfoResponseDto.builder()
                .myProfileImageUrl(tempMember.getProfileImageUrl())
                .nickname(tempMember.getNickname())
                .articleCount(tempMember.getUserInfo().getArticleCount())
                .followerCount(tempMember.getUserInfo().getFollowerCount())
                .followCount(tempMember.getUserInfo().getFollowCount())
                .introduction(tempMember.getUserInfo().getIntroduction())
                .build();
    }

    public ProfileReadResponseDto readProfile(Member member) {
        Member tempMember = memberRepository.findById(member.getId()).get();
        return new ProfileReadResponseDto(tempMember);
    }

    @Transactional
    public ProfileUpdateResponseDto updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto, Member member) {
        Member tempMember = memberRepository.findById(member.getId()).get();
        tempMember.setNickname(profileUpdateRequestDto.getNickname());
        tempMember.setProfileImageUrl(profileUpdateRequestDto.getProfileImageUrl());
        tempMember.getUserInfo().setIntroduction(profileUpdateRequestDto.getIntroduction());
        return new ProfileUpdateResponseDto(tempMember);
    }
}

