package com.example.demo.profile.service;

import com.example.demo.profile.domain.member.Member;
import com.example.demo.profile.domain.member.MemberRepository;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final MemberRepository memberRepository;

    public MyInfoResponseDto readUserInfo(Long userId) {
        Member tempMember = memberRepository.findById(userId).get();

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
}
