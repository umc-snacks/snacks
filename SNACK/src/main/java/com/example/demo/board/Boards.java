package com.example.demo.board;

import com.example.demo.Member.Member;
import com.example.demo.board.comment.Comment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public abstract class Boards extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(name = "WRITER")
    @OneToOne
    private Member writer;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "LIKE")
    private Long like;

    @OneToMany
    private List<Comment> comment = new ArrayList<>();









}
