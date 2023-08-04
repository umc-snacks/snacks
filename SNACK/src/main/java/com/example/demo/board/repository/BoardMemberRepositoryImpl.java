package com.example.demo.board.repository;

import com.example.demo.board.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.example.demo.board.entity.QBoard.board;
import static com.example.demo.board.entity.QBoardMember.boardMember;

@Repository
public class BoardMemberRepositoryImpl implements  BoardMemberRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BoardMemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<Board> searchAttendingBoardsByMemberId(Long memberId) {
        List<Board> boards = jpaQueryFactory
                .select(board)
                .from(board)
                .join(boardMember)
                .on(boardMember.board.id.eq(board.id))
                .where(boardMember.member.id.eq(memberId))
                .fetch();

        return boards;
    }
}
