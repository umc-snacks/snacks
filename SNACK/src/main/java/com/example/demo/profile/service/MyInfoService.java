package com.example.demo.profile.service;

import com.example.demo.Member.MemberRepository;
import com.example.demo.profile.domain.user.User;
import com.example.demo.profile.domain.user.UserRepository;
import com.example.demo.profile.dto.myInfo.MyInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyInfoService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

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
                .build();
    }

    //프로필 수정//
}
