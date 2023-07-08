package com.example.demo.Chat;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@AllArgsConstructor
public class ChatController {
	private final ChatService chatService;
	private final SimpMessagingTemplate template;
	
//	HashSet<String> users = new HashSet<>();
	
	
	/*
	 * 채팅 페이지 진입 시 개인 채팅 및 팀 채팅 정보를 리턴해줌
	 * @param 유저 정보
	 * @return 개인 채팅 및 팀 채팅 list
	 * @DTO 식별자, 이미지 URI, 이름, 읽지 않은 메시지 개수
	 * Exception
	 */ 
	@GetMapping("/chat")
	public ResponseEntity<List<ChatRoomDTO.Get>> connect(/*Principal principal*/) {
		log.info("request chatlist");
		
//		List<ChatRoomDTO.Get> chatList = chatService.getChatList(Long.parseLong(principal.getName()));
		List<ChatRoomDTO.Get> chatList = chatService.getChatList(1L);
		return new ResponseEntity<>(chatList, HttpStatus.OK);
	}
	
	// /pub/chat/room
	@MessageMapping("/chat/{roomNumber}")
	public void connect(@DestinationVariable String roomNumber, MessageDTO messageDTO) {
		
	}
}
