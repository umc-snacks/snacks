package com.example.demo.Chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Chat.Dto.ChatRoomDTO;
import com.example.demo.Chat.Dto.MessageDTO;
import com.example.demo.Chat.Entity.ChatMessage;
import com.example.demo.Chat.Entity.ChatRoom;
import com.example.demo.Chat.Entity.ChatRoomMember;
import com.example.demo.Chat.repository.ChatMessageRepository;
import com.example.demo.Chat.repository.ChatRoomMemberRepository;
import com.example.demo.Chat.repository.ChatRoomRepository;
import com.example.demo.Member.MemberEntity;
import com.example.demo.Member.MemberRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class ChatService {
	private final MemberRepository memberRepository; // 나중에 멤버 서비스의 메소드로 대체할 예정
	private final ChatRoomMemberRepository chatRoomMemberRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final Mapper mapper;
	
	/*
	 * 사용자가 속한 채팅방을 모두 조회하여 리턴한다.
	 * 
	 * @param Long member_id 사용자 식별자
	 * 
	 * @return 사용자가 속한 채팅방 리스트를 DTO로 반환 Exception 사용자가 검색되지 않을 경우 ChatException을
	 * 발생시킴
	 */
	@Transactional(readOnly = true)
	public List<ChatRoomDTO.Get> getChatList(Long member_id) {
		log.info("--getChatList--");
		
		MemberEntity memberEntity = verifiedMember(member_id);

		List<Object[]> crmAndrt = chatRoomMemberRepository.findMembersWithSameRoomAndReceiveTime(member_id);
		List<ChatRoomDTO.Get> chatList = new ArrayList<>();

		Map<Long, ChatRoomDTO.Get> chatMap = new HashMap<>();

	    for (Object[] list : crmAndrt) {
	        ChatRoomMember crm = (ChatRoomMember) list[0];
	        LocalDateTime ldt = (LocalDateTime) list[1];

	        Long roomId = crm.getChatRoom().getRoomId();
	        ChatRoomDTO.Get cr = chatMap.get(roomId);

	        if (cr == null) {
	            cr = new ChatRoomDTO.Get();
	            boolean isTeam = crm.getChatRoom().getTeam() != null;
	            
	            cr.setType(isTeam ? "team" : "private");
	            cr.setRoomId(roomId);
	            cr.setName(isTeam ? crm.getChatRoom().getTeam().getTeamName() : crm.getMember().getNickname());
	            cr.setNumberOfUnreadMessage(chatMessageRepository.getNumberOfUnreadMessage(member_id, roomId));
	            cr.setTime(ldt);
	            
	            chatMap.put(roomId, cr);
	        }
	    }
	    
	    return chatMap.values().stream()
	            .sorted(Comparator.comparing(ChatRoomDTO.Get::getTime).reversed())
	            .collect(Collectors.toList());
	}
	
	/*
	 * 팀에 어떤 사용자가 추가되었을 때 채팅방에 해당 사용자를 추가하는 메서드
	 */
	
	
	
	/*
	 * 채팅방을 생성하는 메서드, A가 B에게 처음 채팅을 걸려고 할 때
	 * 
	 * @param userName B의 닉네임에 해당
	 * @return chatRoom.getRoomId() 
	 */
	public Long createPrivateChatRoom(Long myMemberId, String theOtherMemberName) {
		// 사용자 닉네임으로 찾는 부분
//		MemberEntity member = memberRepository.getByNickName();
		MemberEntity member = new MemberEntity(111L, theOtherMemberName);	// 임시
		
		ChatRoom chatRoom = chatRoomRepository.saveAndFlush(new ChatRoom());
		
		ChatRoomMember chatRoomMemeber = ChatRoomMember.builder()
				.chatRoom(chatRoom)
				.member(member)
				.readTime(LocalDateTime.now())
				.build();
		
		chatRoomMemberRepository.save(chatRoomMemeber);
		
		return chatRoom.getRoomId();
	}
	
	/*
	 * 사용자가 전송한 메시지를 db에 저장함
	 * 
	 * @param MessageDTO.Request mdto 메시지 틀
	 * @return 없음 exception
	 */
	@Transactional
	public void saveMessage(MessageDTO.Request mdto) {
		log.info("--saveMessage--");
		
		ChatRoom ChatRoomEntity = verifiedChatRoom(mdto.getRoomId());
		MemberEntity memberEntity = verifiedMember(mdto.getSenderId());

		ChatMessage chatMessageEntity = ChatMessage.builder()
				.chatRoom(ChatRoomEntity)
				.sender(memberEntity)
				.content(mdto.getContent())
				.sentAt(LocalDateTime.now())
				.build();

		chatMessageRepository.save(chatMessageEntity);
	}
	
	/*
	 * 사용자가 채팅방에 들어갔을 때 안 읽은 메시지들을 보내줌 
	 */
	@Transactional(readOnly = true)
	public List<MessageDTO.Response> getChatMessage(Long memberId, Long roomId) {
		log.info("--getChatMessage--");
		
		List<ChatMessage> unreadChatMessages = chatMessageRepository.getUnreadMessages(memberId, roomId);
		List<MessageDTO.Response> messageDTOResponse = new ArrayList<>();
		
		for(ChatMessage cm : unreadChatMessages) {
			MessageDTO.Response mdtor = mapper.ChatMessageToMessageDtoResponse(cm);
			messageDTOResponse.add(mdtor);
		}
		
		return messageDTOResponse;
	}
	
	/*
	 * 채팅방 삭제
	 */
	@Transactional
	public void deleteChatRoom(Long memberId, Long roomId) {
		log.info("--deleteChatRoom--");
		chatMessageRepository.deleteByRoomId(roomId);
		chatRoomMemberRepository.deleteByRoomId(roomId);
		chatRoomRepository.deleteByRoomId(roomId);
	}
	
	/*
	 * 정기적으로 채팅방에 속한 모든 사용자가 읽은 채팅은 삭제
	 */
//	public void deleteChatMessage() {
//		List<ChatRoom> chatRooms = chatRoomRepository.findAll();
//		
//		for(ChatRoom cr : chatRooms) {
//			chatRoomMemberRepository.findOldestReadTimeByChatRoom(cr);
//			
//		}
//	}
	
	// 사용자가 어떤 채팅방을 읽은 시간을 저장함
	@Transactional
	public void setReadingTime(Long memberId, Long roomId) {
		log.info("--setReadingTime--");
		
		// crm not found 예외 처리 해야함
		ChatRoomMember crm = chatRoomMemberRepository.findByMemberIdAndRoomId(memberId, roomId);
		crm.setReadTime(LocalDateTime.now());
		chatRoomMemberRepository.save(crm);
	}
	
//	public MemberEntity findByMemberName(Long memberId) {
//		log.info("--verifiedMember--");
//		
//		Optional<MemberEntity> optionalMemberEntity = memberRepository.findBy
//	}
	
	@Cacheable(cacheNames = "members")	// 캐싱이 안되는데 원인을 모르겠음
	public MemberEntity verifiedMember(Long memberId) {
		log.info("--verifiedMember--");
		
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
		return optionalMemberEntity.orElseThrow(() -> new ChatException(ErrorCode.MEMBER_NOT_FOUND));
	}

	@Cacheable(cacheNames = "chatRooms")	// 캐싱이 안되는데 원인을 모르겠음
	public ChatRoom verifiedChatRoom(Long roomId) {
		log.info("--verifiedChatRoom--");
		
		Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(roomId);
		return optionalChatRoom.orElseThrow(() -> new ChatException(ErrorCode.ROOM_NOT_FOUND));
	}
}


