package com.example.demo.profile.domain.userinfo;

import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.profile.domain.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@DynamicUpdate
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userInfo", fetch = FetchType.EAGER)
    private Member member;

    @Column(nullable = false)
    private Long articleCount;

    @Column(nullable = false)
    private Long followerCount; // 누가 나를 팔로우

    @Column(nullable = false)
    private Long followCount; // 내가 누구를 팔로우

    @Column(nullable = true)
    private String introduction;

    public UserInfo(Long articleCount, Long followerCount, Long followCount) {
        this.articleCount = articleCount;
        this.followerCount = followerCount;
        this.followCount = followCount;
    }

    public void followerCountPlus(){
        this.followerCount += 1L;
    }

    public void followCountPlus(){
        this.followCount += 1L;
    }

    public void followerCountMinus(){
        this.followerCount -= 1L;
    }

    public void followCountMinus(){
        this.followCount -= 1L;
    }

    public void articleCountPlus() { this.articleCount += 1L; }

    public void articleCountMinus() { this.articleCount -= 1L; }


}
