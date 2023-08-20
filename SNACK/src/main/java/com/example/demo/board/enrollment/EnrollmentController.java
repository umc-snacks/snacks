package com.example.demo.board.enrollment;

import com.example.demo.board.BoardService;
import com.example.demo.board.dto.BoardResponseDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/board/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final BoardService boardService;
    private final EnrollmentRepository enrollmentRepository;


    public EnrollmentController(EnrollmentService enrollmentService, BoardService boardService, EnrollmentRepository enrollmentRepository) {
        this.enrollmentService = enrollmentService;
        this.boardService = boardService;
        this.enrollmentRepository = enrollmentRepository;
    }

    // 유저가 방장에게 요청을 보냄
    @PostMapping
    public ResponseEntity createRequest(@Validated @RequestBody EnrollmentRequestDTO enrollmentRequestDTO
            ,Authentication authentication) throws BoardSizeOverException, BoardMemberOverlappingException, EnrollmentOverlappingException {

        Enrollment enrollment = enrollmentService.convert(enrollmentRequestDTO, authentication);

        ResponseEntity<String> message = autoCheckIn(enrollment);
        if (message != null) return message;

        enrollmentService.saveEnrollment(enrollment);

        return ResponseEntity.ok().body(EnrollmentResponseDTO.toResponseEntity(enrollment));
    }

    private ResponseEntity<String> autoCheckIn(Enrollment enrollment) throws BoardSizeOverException, BoardMemberOverlappingException {
        if (enrollment.getBoard().isAutoCheckIn()) {
            String message = enrollmentService.acceptRequest(enrollment);
            return ResponseEntity.ok().body(message);
        }
        return null;
    }

    @GetMapping("/member")
    public ResponseEntity readMemberRequest(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        List<BoardResponseDTO> boards = new ArrayList<>();
        enrollmentService.readEnrollments(memberId).iterator().forEachRemaining(
                enrollment -> {
                    Board board = boardService.getBoard(enrollment.getId());
                    BoardResponseDTO responseDTO = BoardResponseDTO.getBuild(board);
                    boards.add(responseDTO);
                }
        );
        return ResponseEntity.ok().body(boards);
    }

    // 방장이 요청을 확인하는 메서드
    @GetMapping
    public ResponseEntity readRequest(Authentication authentication) {

        Long hostId = Long.parseLong(authentication.getName());

        List<EnrollmentResponseDTO> enrollments = new ArrayList<>();

        enrollmentService.readEnrollments(hostId).iterator().forEachRemaining(
                enrollment -> enrollments.add(EnrollmentResponseDTO.toResponseEntity(enrollment))
        );

        return ResponseEntity.ok().body(enrollments);
    }

    // 방장이 요청을 체크하는 메서드
    @PostMapping("/{enrollmentId}/{flag}")
    public ResponseEntity acceptRequest(@PathVariable Long enrollmentId, @PathVariable Boolean flag, Authentication authentication) throws BoardSizeOverException, BoardMemberOverlappingException, BoardHostAuthenticationException {
        String message;
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NoSuchElementException("Invalid enrollmentId: " + enrollmentId));

        enrollmentService.validateBoardHost(enrollment, authentication);

        message = processRequest(flag, enrollment);

        enrollmentService.delete(enrollmentId);

        return ResponseEntity.ok().body(message);
    }

    private String processRequest(Boolean flag, Enrollment enrollment) throws BoardSizeOverException, BoardMemberOverlappingException {
        String message;
        if (flag) {
            message = enrollmentService.acceptRequest(enrollment);
        } else {
            message = enrollmentService.denyRequest(enrollment);
        }
        return message;
    }

}
