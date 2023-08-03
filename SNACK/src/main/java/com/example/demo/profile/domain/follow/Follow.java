package com.example.demo.profile.domain.follow;


import org.hibernate.annotations.DynamicUpdate;

import com.example.demo.profile.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Follow  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Member follower; // 팔로우를 하는 사람

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Member followee; // 팔로우를 받는 사람

    @Builder
    public Follow(Member follower, Member followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public void disconnectFollower() {
        this.follower = null;
    }

    public void disconnectFollowee() {
        this.followee = null;
    }
}
