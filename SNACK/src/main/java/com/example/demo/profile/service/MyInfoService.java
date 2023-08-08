package com.example.demo.profile.service;

import com.example.demo.member.entity.Member;
import com.example.demo.profile.domain.userinfo.UserInfoRepository;
import com.example.demo.profile.dto.myInfo.MyInfoArticleResponseDto;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import com.example.demo.profile.dto.profileUpdate.ProfileReadResponseDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateRequestDto;
import com.example.demo.profile.dto.profileUpdate.ProfileUpdateResponseDto;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.socialboard.entity.SocialBoard;
import com.example.demo.socialboard.repository.SocialBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyInfoService {

    private final MemberRepository memberRepository;
    private final UserInfoRepository userInfoRepository;
    private final SocialBoardRepository socialBoardRepository;
    public MyInfoResponseDto readMyInfo(Member tempMember) {
        //Member tempMember = memberRepository.findById(member.getId()).get();

        List<SocialBoard> socialBoardsList = socialBoardRepository.findSocialBoardsByMemberId(tempMember.getId());
        List<MyInfoArticleResponseDto> myInfoArticleResponseDtoList = new ArrayList<>();
        for(SocialBoard article:socialBoardsList){
            myInfoArticleResponseDtoList.add(MyInfoArticleResponseDto.of(article));
        }

        return MyInfoResponseDto.builder()
                .myProfileImageUrl(tempMember.getProfileImageUrl())
                .nickname(tempMember.getNickname())
                .articleCount(tempMember.getUserInfo().getArticleCount())
                .followerCount(tempMember.getUserInfo().getFollowerCount())
                .followCount(tempMember.getUserInfo().getFollowCount())
                .introduction(tempMember.getUserInfo().getIntroduction())
                .myInfoArticleResponseDtoList(myInfoArticleResponseDtoList)
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

