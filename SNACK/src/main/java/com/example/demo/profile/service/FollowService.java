package com.example.demo.profile.service;


import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.profile.domain.follow.FollowRepository;
import com.example.demo.profile.domain.user.User;
import com.example.demo.profile.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public Boolean followUser(Long userId, User user) {
        //User = follow를 하는 사람 (following)
        //userId = follow를 받는 사람 (followed)
        User followedUser = userRepository.findById(userId).get();
        User followingUser = userRepository.findById(user.getId()).get();

        for (Follow follow :followedUser.getFolloweeList()) { //follow 받는 사람의 팔로워 리스트 중 하나
            if(follow.getFollower().getId().equals(followingUser.getId())){ // 그 리스트에 이미 팔로우 하려는 유저가 있다면
                followedUser.getFolloweeList().remove(follow); // 언팔.
                followingUser.getFollowerList().remove(follow);
                follow.disconnectFollowee();
                follow.disconnectFollower();

                followingUser.hasUnFollowing();
                followedUser.hasUnFollowed();

                followRepository.delete(follow);
                return false;
            }
        }
        followRepository.save(Follow.builder()
                .follower(followingUser)
                .followee(followedUser)
                .build());
        followingUser.hasFollowing();
        followedUser.hasFollowed();
        return true;
    }
}
