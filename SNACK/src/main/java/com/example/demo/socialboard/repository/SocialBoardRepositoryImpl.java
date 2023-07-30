package com.example.demo.socialboard.repository;

import com.example.demo.socialboard.entity.SocialBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.demo.socialboard.entity.QSocialBoard.socialBoard;


@Repository
public class SocialBoardRepositoryImpl implements SocialBoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SocialBoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public void updateCount(SocialBoard board1, boolean b) {
        if (b) {
            jpaQueryFactory.update(socialBoard)
                    .set(socialBoard.likes, socialBoard.likes.add(1))
                    .where(socialBoard.eq(board1))
                    .execute();
        } else {
            jpaQueryFactory.update(socialBoard)
                    .set(socialBoard.likes, socialBoard.likes.subtract(1))
                    .where(socialBoard.eq(board1))
                    .execute();
        }
    }
}
