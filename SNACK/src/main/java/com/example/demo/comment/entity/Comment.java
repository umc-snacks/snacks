package com.example.demo.comment.entity;

import com.example.demo.BaseTimeEntity;

import com.example.demo.socialboard.entity.SocialBoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import com.example.demo.entity.Member;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "BOARD_ID")
    private SocialBoard board;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    private List<ReplyComment> replies = new ArrayList<>();

}
