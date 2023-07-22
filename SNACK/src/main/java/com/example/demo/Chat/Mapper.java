package com.example.demo.Chat;

import java.time.LocalDateTime;

import com.example.demo.Chat.Dto.ChatRoomDTO;
import com.example.demo.Chat.Dto.MessageDTO;
import com.example.demo.Chat.Entity.ChatMessage;
import com.example.demo.Chat.Entity.ChatRoomMember;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Mapper {
	public MessageDTO.Response MessageDtoGetToMessageDtoResponse(MessageDTO.Get get) {
		return MessageDTO.Response.builder()
				.senderName(get.getSenderName())
				.content(get.getContent())
				.time(LocalDateTime.now())
				.build();
	}
	
	public MessageDTO.Response ChatMessageToMessageDtoResponse(ChatMessage chatMessage) {
		return MessageDTO.Response.builder()
				.senderName(chatMessage.getSender().getNickname())
				.content(chatMessage.getContent())
				.time(chatMessage.getSentAt())
				.build();
	}
	
}
