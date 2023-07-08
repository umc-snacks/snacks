package com.example.demo.Chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Chat.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{
	
}
