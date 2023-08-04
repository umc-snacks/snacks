package com.example.demo.comment;

import com.example.demo.comment.dto.CommentRequestDTO;
import com.example.demo.comment.dto.CommentResponseDTO;
import com.example.demo.comment.entity.Comment;
import com.example.demo.socialboard.SocialBoardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment/")
@Slf4j
public class CommentController {
    private final SocialBoardService socialBoardService;
    private final CommentService commentService;

    @Autowired
    public CommentController(SocialBoardService socialBoardService, CommentService commentService) {
        this.socialBoardService = socialBoardService;
        this.commentService = commentService;
    }


    @PostMapping("{socialBoardId}")
    public ResponseEntity insert(@PathVariable Long socialBoardId, @Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        Comment comment = commentService.insert(socialBoardId, commentRequestDTO);

        CommentResponseDTO responseDTO = CommentResponseDTO.toResponseEntity(comment);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("{commentId}")
    public ResponseEntity update(@PathVariable Long commentId, @Valid @RequestBody CommentRequestDTO commentRequestDTO) {

        Comment comment = commentService.update(commentId, commentRequestDTO);
        CommentResponseDTO responseDTO = CommentResponseDTO.toResponseEntity(comment);

        return ResponseEntity.ok().body(responseDTO);

    }
}
