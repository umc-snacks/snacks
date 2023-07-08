package com.example.demo.Chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Chat.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{
	
}
