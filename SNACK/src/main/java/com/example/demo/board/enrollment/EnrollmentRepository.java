package com.example.demo.board.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findEnrollmentsByMemberId(Long memberId);

    Optional<Enrollment> findEnrollmentByMemberIdAndBoardId(Long memberId, Long boardId);
}
