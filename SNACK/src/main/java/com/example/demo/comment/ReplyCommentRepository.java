package com.example.demo.comment;

import com.example.demo.comment.entity.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyCommentRepository  extends JpaRepository<ReplyComment, Long> {
}
