package com.example.demo.profile.service;

import com.example.demo.member.entity.Member;
import com.example.demo.profile.dto.myInfo.MyInfoArticleResponseDto;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.socialboard.entity.SocialBoard;
import com.example.demo.socialboard.repository.SocialBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final MemberRepository memberRepository;
    private final SocialBoardRepository socialBoardRepository;

    public MyInfoResponseDto readUserInfo(Long userId) {
        Member tempMember = memberRepository.findById(userId).get();

        List<SocialBoard> socialBoardsList = socialBoardRepository.findSocialBoardsByMemberId(userId);
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
}
