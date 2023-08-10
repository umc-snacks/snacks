package com.example.demo.board.enrollment;

import com.example.demo.Chat.ChatService;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.exception.BoardHostAuthenticationException;
import com.example.demo.member.entity.Member;
import com.example.demo.exception.BoardMemberOverlappingException;
import com.example.demo.exception.BoardSizeOverException;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

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





    public String acceptRequest(Enrollment enrollment) throws BoardSizeOverException, BoardMemberOverlappingException {
        boardSizeValidation(enrollment);
        boardMemberValidation(enrollment);

        BoardMember boardMember = BoardMember.builder()
                .board(enrollment.getBoard())
                .member(enrollment.getMember())
                .build();

        boardMemberRepository.save(boardMember);

        // 사용자가 board에 추가될 경우 chat에도 추가되어야 하기 때문에 추가됨 23.08.08
        chatService.addMemberToChatRoom(boardMember);

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

    public String denyRequest(Enrollment enrollment) {
        // flag가 false인 경우, 거절 메시지를 보내기 또는 원하는 처리를 구현
        return "Enrollment request for board " + enrollment.getBoard().getTitle() + " has been rejected.";
    }

    public void delete(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }

    public void validateBoardHost(Enrollment enrollment, Authentication authentication) throws BoardHostAuthenticationException {
        Long hostId = Long.parseLong(authentication.getName());
        Long writerId = Optional.ofNullable(enrollment)
                .map(Enrollment::getBoard)
                .map(Board::getWriter)
                .map(Member::getId).orElseThrow(() -> new NoSuchElementException("없습"));

        if (hostId != writerId) {
            throw new BoardHostAuthenticationException("게시판의 주인이 아닙니다");
        }

    }

    // 사용자가 요청했을 때 게시판에 받아주는 메서드

    // 방장(writer)이 거절했을 때 거절알림을 주는 메서드

}
