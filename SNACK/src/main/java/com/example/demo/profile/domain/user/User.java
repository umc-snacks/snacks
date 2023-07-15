package com.example.demo.profile.domain.user;

import com.example.demo.profile.domain.follow.Follow;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import com.example.demo.profile.domain.userinfo.UserInfo;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class User  {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String profileImageUrl;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "follower",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Follow> followerList = new ArrayList<>(); //내가 팔로우를 하는 유저들의 리스트

    @OneToMany(mappedBy = "followee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Follow> followeeList = new ArrayList<>(); //나를 팔로우 하는 유저들의 리스트

    @Builder
    public User(String username, String nickname, String password, UserInfo userInfo) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userInfo = userInfo;

        //디폴트 이미지url
        this.profileImageUrl = " ";
    }

    public void hasFollowed(){
        this.userInfo.followerCountPlus();
    }
    public void hasUnFollowed(){
        this.userInfo.followerCountMinus();
    }

    public void hasFollowing(){
        this.userInfo.followCountPlus();
    }
    public void hasUnFollowing(){
        this.userInfo.followCountMinus();
    }


}