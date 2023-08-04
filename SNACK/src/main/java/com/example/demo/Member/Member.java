package com.example.demo.Member;


import com.example.demo.BaseTimeEntity;
import com.example.demo.board.entity.BoardMember;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String nickname;

    private String email;

    @OneToMany(mappedBy = "member")
    private List<BoardMember> memberBoards = new ArrayList<>();


//    public void hasWriteArticle() {
//        this.userInfo.articleCountPlus*();
//    }
}
