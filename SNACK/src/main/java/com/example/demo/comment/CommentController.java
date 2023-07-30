package com.example.demo.comment;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.socialboard.SocialBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/social-board/comment/")
@Slf4j
public class CommentController {
    private final SocialBoardService socialBoardService;
    private final CommentService commentService;

    public CommentController(SocialBoardService socialBoardService, CommentService commentService) {
        this.socialBoardService = socialBoardService;
        this.commentService = commentService;
    }


    @PostMapping("/{boardId}")
    public ResponseEntity insert(@PathVariable Long boardId, @RequestBody CommentDTO commentDTO) {
        commentService.insert(boardId, commentDTO);
        return ResponseEntity.ok().body("s");
    }

}
