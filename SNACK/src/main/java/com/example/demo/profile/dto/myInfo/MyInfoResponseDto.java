package com.example.demo.profile.dto.myInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class MyInfoResponseDto {
    private String myProfileImageUrl;
    private String nickname;
    private Long articleCount;
    private Long followerCount;
    private Long followCount;
    private String introduction;

    //private List<MyInfoArticleResponseDto> myInfoArticleResponseDtoList;
    //게시글 리스트도 리턴

    @Builder
    public MyInfoResponseDto(String myProfileImageUrl, String nickname, Long articleCount, Long followerCount, Long followCount, String introduction){ //,List<MyInfoArticleResponseDto> myInfoArticleResponseDtoList) {
        this.myProfileImageUrl = myProfileImageUrl;
        this.nickname = nickname;
        this.articleCount = articleCount;
        this.followerCount = followerCount;
        this.followCount = followCount;
        this.introduction = introduction;
        //this.myInfoArticleResponseDtoList = myInfoArticleResponseDtoList;
    }
}