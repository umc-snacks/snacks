package com.example.demo.comment;

import com.example.demo.comment.dto.ReplyCommentResponseDTO;
import com.example.demo.comment.dto.ReplyRequestCommentDTO;
import com.example.demo.comment.entity.ReplyComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment/reply/")
public class ReplyCommentController {
    private final ReplyCommentService replyCommentService;

    @Autowired
    public ReplyCommentController(ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @PostMapping("{commentId}")
    public ResponseEntity insert(@PathVariable Long commentId, @RequestBody ReplyRequestCommentDTO replyRequestCommentDTO) {
        ReplyComment replyComment = replyCommentService.insert(commentId, replyRequestCommentDTO);

        ReplyCommentResponseDTO responseDTO = ReplyCommentResponseDTO.getResponseDTO(replyComment);
        return ResponseEntity.ok().body(responseDTO);
    }

}
