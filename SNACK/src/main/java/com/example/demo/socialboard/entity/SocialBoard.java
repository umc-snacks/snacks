package com.example.demo.socialboard.entity;

import com.example.demo.BaseTimeEntity;
import com.example.demo.Member.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class SocialBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "SOCIAL_BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private Member writer;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "LIKES")
    private Long likes = 0L;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    public abstract SocialBoardResponseDTO toResponseEntity();

    public abstract void update(SocialBoard updatedBoard);
}
