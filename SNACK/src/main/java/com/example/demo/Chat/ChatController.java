package com.example.demo.Chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Chat.Dto.ChatRoomDTO;
import com.example.demo.Chat.Dto.MessageDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class ChatController {
	private final ChatService chatService;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final Mapper mapper;
	
	/*
	 * 채팅 페이지 진입 시 개인 채팅 및 팀 채팅 정보를 리턴해줌
	 * @param 유저 정보
	 * @return 개인 채팅 및 팀 채팅 list
	 * @DTO 식별자, 이미지 URI, 이름, 읽지 않은 메시지 개수
	 * Exception
	 */ 
	@GetMapping("/chat")
	public ResponseEntity<List<ChatRoomDTO.Get>> getChatRoomList(/*Principal principal*/) {
		log.info("request chatlist");
		
//		List<ChatRoomDTO.Get> chatList = chatService.getChatList(Long.parseLong(principal.getName()));
		List<ChatRoomDTO.Get> chatList = chatService.getChatList(1L);	// 하드코딩
		
		return new ResponseEntity<>(chatList, HttpStatus.OK);
	}
	
	/*
	 * (개인) 처음으로 대화하거나 방을 나갔다가 다시 대화 생성하는 경우
	 */
	@PatchMapping("/chat/{name}")
	public ResponseEntity createChatRoom(/*Principal principal,*/ @PathVariable("name") String theOtherMemberName) {
		Long myMemberId = 1L;
		Long roomId = chatService.createPrivateChatRoom(myMemberId, theOtherMemberName);
		return new ResponseEntity<>(roomId, HttpStatus.OK);
	}
	
	/*
	 * 채팅방에 들어갔을 때 안 읽은 메시지들을 리턴해줌
	 * @param principal 유저정보, roomId 방 식별자
	 * @return 안 읽은 메시지들
	 * exception
	 */
	@GetMapping("/chat/{roomId}")
	public ResponseEntity<List<MessageDTO.Response>> enterChatRoom(/*Principal principal,*/ @PathVariable("roomId") Long roomId) {
//		List<MessageDTO.Response> msgList = chatService.getChatMessage(Long.parseLong(principal.getName()), roomId);
		
		List<MessageDTO.Response> msgList = chatService.getChatMessage(4L, 1L);	// 하드 코딩
		
		return new ResponseEntity<>(msgList, HttpStatus.OK);
	}
	
	/*
	 * 사용자가 메시지를 전송하면 저장하고 같은 방에 있는 사용자에게 메시지를 반환함
	 * @param
	 * @DTO senderName 이름, content 내용
	 */
	@MessageMapping("/chat/{roomId}") // '/pub/chat/roomId'
	public void sendMessage(/*Principal principal,*/ @DestinationVariable int roomId, @RequestBody MessageDTO.Get messageDTO) {
//		MessageDTO.Request mdto = new MessageDTO.Request(Long.parseLong(principal.getName()), roomId, messageDTO.getContent(), messageDTO.getTime());
		MessageDTO.Request mdto = new MessageDTO.Request((long)2, (long)roomId, messageDTO.getContent());	// 하드코딩
		
		chatService.saveMessage(mdto);
		
		MessageDTO.Response response = mapper.MessageDtoGetToMessageDtoResponse(messageDTO);
		
		simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, response); 
	}
	
	/*
	 * 사용자가 채팅방을 읽은 시각을 업데이트 함
	 */
	@PatchMapping("/chat/reading")
	public void setReadingTime(/*Principal principal,*/ @RequestBody ChatRoomDTO.Get dto) {
		//chatService.patchReadingTime(Long.parseLong(principal.getName()), dto.getRoomId());
		chatService.setReadingTime(1L, dto.getRoomId());
	}
	
	@DeleteMapping("/chat/{roomId}")
	public void deleteChatRoom(/*Principal principal,*/ @PathVariable("roomId") Long roomId) {
//		chatService.deleteChatRoom(Long.parseLong(principal.getName()), roomId);
		chatService.deleteChatRoom(1L, roomId);
	}
}
