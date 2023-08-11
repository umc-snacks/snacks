package com.example.demo.profile.domain.userinfo;

import com.example.demo.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userInfo")
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
        this.introduction= "default_introduction";
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
