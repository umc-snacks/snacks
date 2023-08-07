package com.example.demo.board.enrollment;

import com.example.demo.Chat.ChatService;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.entity.Member;
import com.example.demo.exception.BoardMemberOverlappingException;
import com.example.demo.exception.BoardSizeOverException;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class EnrollmentService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ChatService chatService;

    public EnrollmentService(BoardRepository boardRepository, MemberRepository memberRepository, BoardMemberRepository boardMemberRepository, EnrollmentRepository enrollmentRepository, ChatService chatService) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.chatService = chatService;
    }

    public Enrollment convert(EnrollmentRequestDTO enrollment) {
        Member member = memberRepository.findById(enrollment.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + enrollment.getMemberId()));

        Board board = boardRepository.findById(enrollment.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + enrollment.getBoardId()));

        return Enrollment.builder()
                .member(member)
                .board(board)
                .build();
    }
    public List<Enrollment> readEnrollments(Long hostId) {
        List<Enrollment> enrollments = boardMemberRepository.searchHostRequestByHostId(hostId);
        return enrollments;
    }
    public void createEnrollment(Enrollment enrollment) throws BoardSizeOverException {
        boardSizeValidation(enrollment);
        enrollmentRepository.save(enrollment);
    }





    public String acceptRequest(Long enrollmentId) throws BoardSizeOverException, BoardMemberOverlappingException {

        // flag가 true인 경우, enrollment에 있는 멤버를 해당 board의 멤버로 넣어주기
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NoSuchElementException("Invalid enrollmentId: " + enrollmentId));

        boardSizeValidation(enrollment);
        boardMemberValidation(enrollment);

        BoardMember boardMember = BoardMember.builder()
                .board(enrollment.getBoard())
                .member(enrollment.getMember())
                .build();

        boardMemberRepository.save(boardMember);
        return "Enrollment request for board " + enrollment.getBoard().getTitle() + " successes";
    }

        private void boardMemberValidation(Enrollment enrollment) throws BoardMemberOverlappingException {
            List<BoardMember> boardMembers = boardMemberRepository.findBoardMembersByBoardId(enrollment.getBoard().getId());
            for (BoardMember bm : boardMembers) {
                if (Objects.equals(bm.getMember().getId(), enrollment.getMember().getId())) throw new BoardMemberOverlappingException("이미 게시글에 등록된 회원입니다.");
            }
        }

        private static void boardSizeValidation(Enrollment enrollment) throws BoardSizeOverException {
                Board board = enrollment.getBoard();
                if (board.getBoardMembers().size() >= board.getMaxCount()) {
                    throw new BoardSizeOverException("멤버수가 초과하였습니다.");
                }
            }

    public String denyRequest(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NoSuchElementException("Invalid enrollmentId: " + enrollmentId));

        // flag가 false인 경우, 거절 메시지를 보내기 또는 원하는 처리를 구현
        return "Enrollment request for board " + enrollment.getBoard().getTitle() + " has been rejected.";
    }

    public void delete(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }

    // 사용자가 요청했을 때 게시판에 받아주는 메서드

    // 방장(writer)이 거절했을 때 거절알림을 주는 메서드

}
