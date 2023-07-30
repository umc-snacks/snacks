package com.example.demo.profile.domain;



import com.example.demo.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;

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
    private MemberEntity follower; // 팔로우를 하는 사람

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private MemberEntity followee; // 팔로우를 받는 사람

    @Builder
    public Follow(MemberEntity follower, MemberEntity followee) {
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