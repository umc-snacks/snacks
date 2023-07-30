package com.example.demo.comment.entity;

import jakarta.persistence.*;

@Entity
public class ReplyComment {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "REPLY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;
}
