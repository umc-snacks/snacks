package com.example.demo.Chat;

import com.example.demo.Chat.Dto.ChatRoomDTO;
import com.example.demo.Chat.Dto.ChatRoomListDTO;
import com.example.demo.Chat.Dto.MessageDTO;
import com.example.demo.exception.RestApiException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ChatController {
	private final ChatService chatService;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final Mapper mapper;
	
	/*
	 * 채팅 페이지 진입 시 개인 채팅 및 팀 채팅 정보를 리턴해줌
	 * 
	 * @param 유저 정보
	 * @return 개인 채팅 및 팀 채팅 list
	 * @DTO 식별자, 이미지 URI, 이름, 읽지 않은 메시지 개수
	 * Exception
	 */ 
	@GetMapping("/chat")
	public ResponseEntity<Object> getChatRoomList(Authentication authentication) {
//		log.info("[ChatController getChatRoomList] memberId : " + authentication.getName());

//		ChatRoomListDTO chatRoomListDTO = chatService.getChatList(Long.parseLong(authentication.getName()));
		ChatRoomListDTO chatRoomListDTO = chatService.getChatList(1L);

		return new ResponseEntity<>(chatRoomListDTO, HttpStatus.OK);
	}
	
	/*
	 * (개인) 처음으로 대화하거나 방을 나갔다가 다시 대화 생성하는 경우
	 */
	@PostMapping("/chat/{memberId}")
	public ResponseEntity createChatRoom(Authentication authentication, @PathVariable("memberId") Long participantId) throws RestApiException {
//		log.info("[ChatController createChatRoom] memberId : " + authentication.getName() + " participantId : " + participantId);

//		Long roomId = chatService.createPrivateChatRoom(Long.parseLong(authentication.getName()), participantId);
		Long roomId = chatService.createPrivateChatRoom(1L, 2L);

		return new ResponseEntity<>(roomId, HttpStatus.OK);
	}
	
	/*
	 * 채팅방에 들어갔을 때 안 읽은 메시지들을 리턴해줌
	 * 
	 * @param principal 유저정보, roomId 방 식별자
	 * @return 안 읽은 메시지들
	 * exception
	 */
	@GetMapping("/chat/{roomId}")
	public ResponseEntity<List<MessageDTO.Response>> enterChatRoom(Authentication authentication, @PathVariable("roomId") Long roomId) {
//		log.info("[ChatController enterChatRoom] memberId : " + authentication.getName() + " roomId : " + roomId);
//		List<MessageDTO.Response> msgList = chatService.getChatMessage(Long.parseLong(authentication.getName()), roomId);
		List<MessageDTO.Response> msgList = chatService.getChatMessage(1L, roomId);

		return new ResponseEntity<>(msgList, HttpStatus.OK);
	}
	
	/*
	 * 사용자가 메시지를 전송하면 저장하고 같은 방에 있는 사용자에게 메시지를 반환함
	 * 
	 * @param
	 * @DTO senderName 이름, content 내용
	 */
	@MessageMapping("/chat/{roomId}") // '/pub/chat/roomId'
	public void messageSendAndSave(Authentication authentication, @DestinationVariable Long roomId, @Valid @RequestBody MessageDTO.Get messageDTO) {
//		log.info("[ChatController messageSendAndSave] memberId : " + authentication.getName() +
//				" senderName : " + messageDTO.getSenderName() +
//				" Content: " + messageDTO.getContent());

//		MessageDTO.Request mdto = new MessageDTO.Request(Long.parseLong(authentication.getName()), roomId, messageDTO.getContent());
		MessageDTO.Request mdto = new MessageDTO.Request(1L, 1L, messageDTO.getContent());
		chatService.saveMessage(mdto);
		
		MessageDTO.Response response = mapper.MessageDtoGetToMessageDtoResponse(messageDTO);
		
		simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, response); 
	}
	
	/*
	 * 사용자가 채팅방을 읽은 시각을 업데이트 함
	 */
	@PatchMapping("/chat/reading")
	public void setReadingTime(Authentication authentication, @Valid @RequestBody ChatRoomDTO.Get dto) throws MethodArgumentTypeMismatchException, RestApiException {
//		log.info("[ChatController setReadingTime] memberId : " + authentication.getName() + " roomId : " + dto.getRoomId());

//		chatService.setReadingTime(Long.parseLong(authentication.getName()), dto.getRoomId());
		chatService.setReadingTime(1L, dto.getRoomId());
	}
	
	@DeleteMapping("/chat/{roomId}")
	public void deleteChatRoom(Authentication authentication, @PathVariable("roomId") Long roomId) {
//		log.info("[ChatController deleteChatRoom] memberId : " + authentication.getName() + " roomId : " + roomId);

//		chatService.deleteChatRoom(Long.parseLong(authentication.getName()), roomId);
		chatService.deleteChatRoom(1L, roomId);
	}
}
