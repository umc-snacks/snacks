package com.example.demo.heart;

import com.example.demo.entity.Member;
import com.example.demo.socialboard.entity.SocialBoard;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HEART_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SOCIAL_BOARD_ID")
    private SocialBoard socialBoard;
}
