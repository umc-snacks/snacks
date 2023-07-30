package com.example.demo.socialboard.repository;

import com.example.demo.socialboard.entity.SocialBoard;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialBoardRepositoryCustom {
    void updateCount(SocialBoard board, boolean b);

}
