package com.example.demo.board.repository;

import com.example.demo.board.entity.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long>, BoardMemberRepositoryCustom {


//    public List<BoardMember> findBoardMembersByMemberId(Long memberId);

    public List<BoardMember> findBoardMembersByBoardId(Long boardId);
}
