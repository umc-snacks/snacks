package com.example.demo.Chat;

import java.time.LocalDateTime;

import com.example.demo.Chat.Dto.MemberSearchDTO;
import com.example.demo.Chat.Dto.MessageDTO;
import com.example.demo.Chat.Entity.ChatMessage;
import com.example.demo.profile.domain.member.Member;

import lombok.NoArgsConstructor;

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
