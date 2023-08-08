package com.example.demo.member.entity;

import com.example.demo.member.dto.MemberDTO;

import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.profile.domain.userinfo.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)

    private String loginId;

    @Column
    private String pw;

    @Column
    private String name;

    @Column(unique = true) //unique 제약조건 추가
    private String nickname;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") //날짜 포멧 바꾸기
    private LocalDate birth;

    @Column
    private String profileimageurl;

    @OneToMany(mappedBy = "follower",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Follow> followerList = new ArrayList<>(); //내가 팔로우를 하는 유저들의 리스트

    @OneToMany(mappedBy = "followee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Follow> followeeList = new ArrayList<>(); //나를 팔로우 하는 유저들의 리스트

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private UserInfo userInfo;

    public static Member toMemberEntity(MemberDTO memberDTO, PasswordEncoder passwordEncoder, UserInfo userInfo) {
        Member member = new Member();
        member.setLoginId(memberDTO.getLoginId());
        member.setPw(passwordEncoder.encode(memberDTO.getPw()));
        member.setName(memberDTO.getName());
        member.setNickname(memberDTO.getNickname());
        member.setBirth(memberDTO.getBirth());
        member.setUserInfo(userInfo);
        return member;
    }
    public static Member toMemberEntity_login(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {

        Member member = new Member();
        member.setLoginId(memberDTO.getLoginId());
        member.setPw(passwordEncoder.encode(memberDTO.getPw()));
        member.setName(memberDTO.getName());
        member.setNickname(memberDTO.getNickname());
        member.setBirth(memberDTO.getBirth());

        return member;
    }


    public static Member toMemberEntity_with_newpw(MemberDTO memberDTO, String new_pw, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setLoginId(memberDTO.getLoginId());
        member.setPw(passwordEncoder.encode(new_pw));
        member.setName(memberDTO.getName());
        member.setNickname(memberDTO.getNickname());
        member.setBirth(memberDTO.getBirth());
        return member;
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

    public void hasWroteArticle(){
        this.userInfo.articleCountPlus();
    }
    public void hasDeletedArticle(){
        this.userInfo.articleCountMinus();
    }
}


