package com.example.demo.Chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.demo.Chat.Dto.ChatRoomDTO;
import com.example.demo.Chat.Dto.ChatRoomListDTO;
import com.example.demo.Chat.Dto.MessageDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Chat", description = "채팅 API입니다.")
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
	@Operation(summary = "채팅 리스트 메서드", description = "채팅 페이지에 진입시 사용자의 모든 채팅 내용을 반환하는 메서드입니다.")
	@GetMapping("/chat")
	public ResponseEntity<Object> getChatRoomList(/*Principal principal*/) {
		log.info("request chatlist");
		
//		List<ChatRoomDTO.Get> chatList = chatService.getChatList(Long.parseLong(principal.getName()));
		ChatRoomListDTO chatRoomList = chatService.getChatList(1L);	// 하드코딩
		
		return new ResponseEntity<>(chatRoomList, HttpStatus.OK);
	}
	
	/*
	 * (개인) 처음으로 대화하거나 방을 나갔다가 다시 대화 생성하는 경우
	 */
	@Operation(summary = "", description = "채팅방에서 나올 때 해당 사용자의 채팅방에서 읽은 시각을 기록합니다.")
	@PostMapping("/chat/{memberId}")
	public ResponseEntity createChatRoom(/*Principal principal,*/ @PathVariable("memberId") int participantId) {
		Long creatorId = 1L;
//		Long roomId = chatService.createPrivateChatRoom(myMemberId, (Long.parseLong(principal.getName(participantId)));
		Long roomId = chatService.createPrivateChatRoom(creatorId, 5L);
		
		return new ResponseEntity<>(roomId, HttpStatus.OK);
	}
	
	/*
	 * 채팅방에 들어갔을 때 안 읽은 메시지들을 리턴해줌
	 * 
	 * @param principal 유저정보, roomId 방 식별자
	 * @return 안 읽은 메시지들
	 * exception
	 */
	@Operation(summary = "채팅방 입장시 안 읽은 메시지 반환", description = "채팅방에 입장했을 때 오프라인 상태여서 안 읽었던 메시지를 반환합니다.")
	@GetMapping("/chat/{roomId}")
	public ResponseEntity<List<MessageDTO.Response>> enterChatRoom(/*Principal principal,*/ @Positive @PathVariable("roomId") Long roomId) {
//		List<MessageDTO.Response> msgList = chatService.getChatMessage(Long.parseLong(principal.getName()), roomId);
		
		List<MessageDTO.Response> msgList = chatService.getChatMessage(1L, roomId);	// 하드 코딩
		
		return new ResponseEntity<>(msgList, HttpStatus.OK);
	}
	
	/*
	 * 사용자가 메시지를 전송하면 저장하고 같은 방에 있는 사용자에게 메시지를 반환함
	 * 
	 * @param
	 * @DTO senderName 이름, content 내용
	 */
	@Operation(summary = "메시지 전송", description = "사용자가 채팅방에서 메시지를 전송하면 채팅방에 온라인인 모든 사용자에게 메시지를 반환하며, 오프라인 사용자를 위해 메시지를 DB에 저장합니다.")
	@MessageMapping("/chat/{roomId}") // '/pub/chat/roomId'
	public void messageSendAndSave(/*Principal principal,*/ @Positive @DestinationVariable int roomId, @RequestBody MessageDTO.Get messageDTO) {
//		MessageDTO.Request mdto = new MessageDTO.Request(Long.parseLong(principal.getName()), roomId, messageDTO.getContent(), messageDTO.getTime());
		MessageDTO.Request mdto = new MessageDTO.Request((long)1, (long)roomId, messageDTO.getContent());	// 하드코딩
		chatService.saveMessage(mdto);
		
		MessageDTO.Response response = mapper.MessageDtoGetToMessageDtoResponse(messageDTO);
		
		simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, response); 
	}
	
	/*
	 * 사용자가 채팅방을 읽은 시각을 업데이트 함
	 */
	@Operation(summary = "읽은 시각 기록", description = "채팅방에서 나올 때 해당 사용자의 채팅방에서 읽은 시각을 기록합니다.")
	@PatchMapping("/chat/reading")
	public void setReadingTime(/*Principal principal,*/ @Positive @RequestBody ChatRoomDTO.Get dto) throws MethodArgumentTypeMismatchException {
		//chatService.patchReadingTime(Long.parseLong(principal.getName()), dto.getRoomId());
		chatService.setReadingTime(1L, dto.getRoomId());
	}
	
	@DeleteMapping("/chat/{roomId}")
	public void deleteChatRoom(/*Principal principal,*/ @PathVariable("roomId") Long roomId) {
//		chatService.deleteChatRoom(Long.parseLong(principal.getName()), roomId);
		chatService.deleteChatRoom(1L, roomId);
	}
}
