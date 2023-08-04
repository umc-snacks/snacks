package com.example.demo.profile.service;


import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.profile.domain.follow.FollowRepository;
import com.example.demo.profile.domain.member.Member;
import com.example.demo.profile.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public Boolean followUser(Long userId, Member member) {
        //User = follow를 하는 사람 (following)
        //userId = follow를 받는 사람 (followed)
        Member followedMember = memberRepository.findById(userId).get();
        Member followingMember = memberRepository.findById(member.getId()).get();

        for (Follow follow : followedMember.getFolloweeList()) { //follow 받는 사람의 팔로워 리스트 중 하나
            if(follow.getFollower().getId().equals(followingMember.getId())){ // 그 리스트에 이미 팔로우 하려는 유저가 있다면
                followedMember.getFolloweeList().remove(follow); // 언팔.
                followingMember.getFollowerList().remove(follow);
                follow.disconnectFollowee();
                follow.disconnectFollower();
                
                followingMember.hasUnFollowing();
                followedMember.hasUnFollowed();

                followRepository.delete(follow);
                return false;
            }
        }
        followRepository.save(Follow.builder()
                .follower(followingMember)
                .followee(followedMember)
                .build());
        followingMember.hasFollowing();
        followedMember.hasFollowed();
        return true;
    }
}
