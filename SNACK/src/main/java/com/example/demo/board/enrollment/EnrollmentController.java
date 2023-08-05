package com.example.demo.board.enrollment;

import com.example.demo.exception.BoardMemberOverlappingException;
import com.example.demo.exception.BoardSizeOverException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/board/enrollment/")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
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
    @GetMapping("{hostId}")
    public ResponseEntity readRequest(@PathVariable Long hostId) {
        List<EnrollmentResponseDTO> enrollments = new ArrayList<>();

        enrollmentService.readEnrollments(hostId).iterator().forEachRemaining(
                enrollment -> enrollments.add(EnrollmentResponseDTO.toResponseEntity(enrollment))
        );

        return ResponseEntity.ok().body(enrollments);
    }

    // 방장이 요청을 체크하는 메서드
    @PostMapping("{enrollmentId}/{flag}")
    public ResponseEntity checkRequest(@PathVariable Long enrollmentId, @PathVariable Boolean flag) throws BoardSizeOverException, BoardMemberOverlappingException {
        String message;
        if (flag) {
            message = enrollmentService.acceptRequest(enrollmentId);
        } else {
            message = enrollmentService.denyRequest(enrollmentId);
        }
        enrollmentService.delete(enrollmentId);

        return ResponseEntity.ok().body(message);
    }

}
