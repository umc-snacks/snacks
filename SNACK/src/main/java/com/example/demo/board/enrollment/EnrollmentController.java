package com.example.demo.board.enrollment;

import com.example.demo.exception.BoardHostAuthenticationException;
import com.example.demo.exception.BoardMemberOverlappingException;
import com.example.demo.exception.BoardSizeOverException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/board/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final EnrollmentRepository enrollmentRepository;


    public EnrollmentController(EnrollmentService enrollmentService, EnrollmentRepository enrollmentRepository) {
        this.enrollmentService = enrollmentService;
        this.enrollmentRepository = enrollmentRepository;
    }

    // 유저가 방장에게 요청을 보냄
    @PostMapping
    public ResponseEntity createRequest(@Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO) throws BoardSizeOverException {
        Enrollment enrollment = enrollmentService.convert(enrollmentRequestDTO);
        enrollmentService.createEnrollment(enrollment);

        return ResponseEntity.ok().body(EnrollmentResponseDTO.toResponseEntity(enrollment));
    }
    // 유저가 요청을 취소함

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
    public ResponseEntity checkRequest(@PathVariable Long enrollmentId, @PathVariable Boolean flag, Authentication authentication) throws BoardSizeOverException, BoardMemberOverlappingException, BoardHostAuthenticationException {
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
