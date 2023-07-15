package com.example.demo.profile.domain.userinfo;

import com.example.demo.profile.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userInfo", fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
    private Long articleCount;

    @Column(nullable = false)
    private Long followerCount; // 누가 나를 팔로우

    @Column(nullable = false)
    private Long followCount; // 내가 누구를 팔로우

    @Column(nullable = false)
    private String introduction;


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
}
