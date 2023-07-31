package com.example.demo.comment;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.dto.ReplyCommentDTO;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.entity.ReplyComment;
import com.example.demo.socialboard.SocialBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment/reply/")
public class ReplyCommentController {
    private final ReplyCommentService replyCommentService;

    @Autowired
    public ReplyCommentController(ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @PostMapping({"commentId"})
    public ResponseEntity insert(@PathVariable Long commentId, @RequestBody ReplyCommentDTO replyCommentDTO) {
        ReplyComment replyComment = replyCommentService.insert(commentId, replyCommentDTO);
        return ResponseEntity.ok().body(replyComment);
    }

}
