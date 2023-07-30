package com.example.demo.socialboard.repository;

import com.example.demo.socialboard.entity.SocialBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialBoardRepository extends JpaRepository<SocialBoard, Long>, SocialBoardRepositoryCustom {
}
