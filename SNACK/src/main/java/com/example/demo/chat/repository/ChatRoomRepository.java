package com.example.demo.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.chat.Entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{
	
	@Modifying
	@Query("DELETE FROM ChatRoom cr WHERE cr.roomId = :roomId")
	int deleteByRoomId(@Param("roomId") Long roomId);
	
	@Query("SELECT cr FROM ChatRoom cr WHERE cr.board.id = :teamId")
	ChatRoom findByTeamId(@Param("teamId") Long teamId);
}
