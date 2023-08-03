package com.example.demo.profile.domain.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
	
	@Query("SELECT f FROM Follow f WHERE f.followee.id = :memberId")
	List<Follow> findAllMyFollowr(@Param("memberId") Long memberId);
	
	@Query("SELECT f FROM Follow f WHERE f.follower.id = :memberId")
	List<Follow> findAllMyFollowee(@Param("memberId") Long memberId);
}
