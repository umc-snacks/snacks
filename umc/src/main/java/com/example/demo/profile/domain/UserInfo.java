package com.example.demo.profile.domain;


import com.example.demo.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter @Setter
@NoArgsConstructor
@DynamicUpdate
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToOne(mappedBy = "userInfo", fetch = FetchType.EAGER)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private MemberEntity member;

    @Column(nullable = false)
    private Long articleCount;

    @Column(nullable = false)
    private Long followerCount; // 누가 나를 팔로우

    @Column(nullable = false)
    private Long followCount; // 내가 누구를 팔로우

    @Column(nullable = false)
    private String introduction;

    public UserInfo(Long articleCount, Long followerCount, Long followCount) {
        this.articleCount = articleCount;
        this.followerCount = followerCount;
        this.followCount = followCount;
        this.introduction = "default introduction";
    }


    // 08.03 창민 추가영역!!! 시작
    public static class UserInfoBuilder {
        private Long articleCount = 0L;
        private Long followerCount = 0L;
        private Long followCount = 0L;

        UserInfoBuilder() {
        }

        public UserInfoBuilder articleCount(Long articleCount) {
            this.articleCount = articleCount;
            return this;
        }

        public UserInfoBuilder followerCount(Long followerCount) {
            this.followerCount = followerCount;
            return this;
        }

        public UserInfoBuilder followCount(Long followCount) {
            this.followCount = followCount;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(articleCount, followerCount, followCount);
        }
    }

    // 08.03 창민 추가영역!!! 끝!

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




    public static UserInfoBuilder builder() {
        return new UserInfoBuilder();
    }
}
