package com.example.demo.entity;

import com.example.demo.dto.MemberDTO;
import com.example.demo.profile.domain.Follow;
import com.example.demo.profile.domain.UserInfo;
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
@Table(name = "member_table_07_27")
public class MemberEntity {

    @Id//pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberLoginId;

    @Column(unique = true)
    private String id;

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



    /*
    //08/03 원본태연코드
    public static MemberEntity toMemberEntity(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity = new MemberEntity();
        //memberEntity.setMemberLoginId(memberDTO.getMemberLoginId());
        memberEntity.setId(memberDTO.getId());
        memberEntity.setPw(passwordEncoder.encode(memberDTO.getPw()));
        memberEntity.setName(memberDTO.getName());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setBirth(memberDTO.getBirth());
        return memberEntity;
    }*/

    // 08/03 창민-태연 코드

    public static MemberEntity toMemberEntity(MemberDTO memberDTO, PasswordEncoder passwordEncoder, UserInfo userInfo) {
        MemberEntity memberEntity = new MemberEntity();
        //memberEntity.setMemberLoginId(memberDTO.getMemberLoginId());
        memberEntity.setId(memberDTO.getId());
        memberEntity.setPw(passwordEncoder.encode(memberDTO.getPw()));
        memberEntity.setName(memberDTO.getName());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setBirth(memberDTO.getBirth());
        memberEntity.setUserInfo(userInfo);
        return memberEntity;
    }
    public static MemberEntity toMemberEntity_login(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {

        MemberEntity memberEntity = new MemberEntity();
        //memberEntity.setMemberLoginId(memberDTO.getMemberLoginId());
        memberEntity.setId(memberDTO.getId());
        memberEntity.setPw(passwordEncoder.encode(memberDTO.getPw()));
        memberEntity.setName(memberDTO.getName());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setBirth(memberDTO.getBirth());

        return memberEntity;
    }


    public static MemberEntity toMemberEntity_with_newpw(MemberDTO memberDTO,String new_pw, PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberLoginId(memberDTO.getMemberLoginId());
        memberEntity.setId(memberDTO.getId());
        memberEntity.setPw(passwordEncoder.encode(new_pw));
        memberEntity.setName(memberDTO.getName());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setBirth(memberDTO.getBirth());
        return memberEntity;
    }




}


