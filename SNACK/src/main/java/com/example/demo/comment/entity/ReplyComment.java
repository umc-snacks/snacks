package com.example.demo.comment.entity;

import com.example.demo.BaseTimeEntity;
import com.example.demo.Member.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyComment extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REPLY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member writer;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;


}
