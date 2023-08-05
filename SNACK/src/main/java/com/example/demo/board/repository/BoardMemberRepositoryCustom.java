package com.example.demo.board.repository;

import com.example.demo.Member.Member;
import com.example.demo.board.enrollment.Enrollment;
import com.example.demo.board.entity.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMemberRepositoryCustom {
    List<Board> searchAttendingBoardsByMemberId(Long memberId);
    List<Enrollment> searchHostRequestByHostId(Long hostId);

}
