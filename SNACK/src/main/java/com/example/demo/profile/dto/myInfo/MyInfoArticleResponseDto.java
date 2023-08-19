package com.example.demo.profile.dto.myInfo;

import com.example.demo.socialboard.entity.SocialBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyInfoArticleResponseDto {

    private Long articleId;
    private String articleImageUrl;

    @Builder
    public MyInfoArticleResponseDto(Long articleId){//, String articleImageUrl) {
        this.articleId = articleId;
        //this.articleImageUrl = articleImageUrl;
    }

    public static MyInfoArticleResponseDto of(SocialBoard entity){
        return new MyInfoArticleResponseDto(entity.getId());//, entity.getImageUrl());
    }
}
