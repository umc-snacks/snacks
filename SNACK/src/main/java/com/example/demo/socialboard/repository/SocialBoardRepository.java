package com.example.demo.socialboard.repository;

import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.socialboard.entity.SocialBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialBoardRepository extends JpaRepository<SocialBoard, Long>, SocialBoardRepositoryCustom {
    //public List<SocialBoard> findSocialBoardsByMemberId(Long id);

    @Query("SELECT s FROM SocialBoard s WHERE s.writer.id = :memberId")
    public List<SocialBoard> findSocialBoardsByMemberId(@Param("memberId") Long memberId);

}
