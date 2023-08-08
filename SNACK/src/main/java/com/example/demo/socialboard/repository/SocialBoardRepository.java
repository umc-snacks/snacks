package com.example.demo.socialboard.repository;

import com.example.demo.socialboard.entity.SocialBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialBoardRepository extends JpaRepository<SocialBoard, Long>, SocialBoardRepositoryCustom {
    public List<SocialBoard> findSocialBoardsByMemberId(Long memberId);
}
