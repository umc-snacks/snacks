package com.example.demo.board.repository;

import com.example.demo.board.enrollment.Enrollment;
import com.example.demo.board.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.example.demo.board.entity.QBoard.board;

import static com.example.demo.board.enrollment.QEnrollment.enrollment;

import static com.example.demo.board.entity.QBoardMember.boardMember;

@Repository
public class BoardMemberRepositoryImpl implements  BoardMemberRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private final BoardRepository boardRepository;


    @Autowired
    public BoardMemberRepositoryImpl(JPAQueryFactory jpaQueryFactory, BoardRepository boardRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.boardRepository = boardRepository;
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


    // 방장 아이디를 받고 방장이 개설한 방을 받고 그 방이
    // enrollment 요청 멤버 아이디, 방 아이디
    // Board 방장 아이디, 멤버 목록
    // Member

    // 1. host입장에서 관리하고있는 방목록을 얻어온다음 enrollment에서 검색

    // 뭘 받고싶은건데? -> enrollment를 받고 싶다

    public List<Enrollment> searchHostRequestByHostId(Long hostId) {
        return jpaQueryFactory.select(enrollment)
                .from(enrollment)
                .join(enrollment.board, board)
                .on(enrollment.board.id.eq(board.id))
                .where(board.writer.id.eq(hostId))
                .fetch();
    }

}
