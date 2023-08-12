package com.example.demo.Chat;

import com.example.demo.Chat.Dto.MemberSearchDTO;
import com.example.demo.Chat.Dto.MessageDTO;
import com.example.demo.Chat.Entity.ChatMessage;
import com.example.demo.member.entity.Member;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class Mapper {
	public static MessageDTO.Response MessageDtoGetToMessageDtoResponse(MessageDTO.Get get) {
		return MessageDTO.Response.builder()
				.senderName(get.getSenderName())
				.content(get.getContent())
				.time(LocalDateTime.now())
				.build();
	}
	
	public static MessageDTO.Response ChatMessageToMessageDtoResponse(ChatMessage chatMessage) {
		return MessageDTO.Response.builder()
				.memberId(chatMessage.getSender().getId())
				.senderName(chatMessage.getSender().getNickname())
				.content(chatMessage.getContent())
				.time(chatMessage.getSentAt())
				.build();
	}
	
	public static MemberSearchDTO MemberToMemberSearchDTO(Member member) {
		return MemberSearchDTO.builder()
				.nickName(member.getNickname().toString())
				.memberId(member.getId())
				.build();
	}
	
}
