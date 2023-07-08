package com.example.demo.Chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Chat.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
	@Query("SELECT COUNT(*) FROM ChatMessage cm WHERE cm.chatRoom.id = :chatRoom_id AND cm.isRead = false")
	int getUnReadMessageCount(@Param("chatRoom_id") Long chatRoom_id);
}
