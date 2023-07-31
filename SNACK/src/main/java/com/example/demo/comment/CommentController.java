package com.example.demo.comment;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.dto.CommentResponseDTO;
import com.example.demo.comment.entity.Comment;
import com.example.demo.socialboard.SocialBoardService;
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
    public ResponseEntity insert(@PathVariable Long socialBoardId, @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.insert(socialBoardId, commentDTO);

        CommentResponseDTO responseDTO = CommentResponseDTO.toResponseEntity(comment);
        return ResponseEntity.ok().body(responseDTO);
    }


}
