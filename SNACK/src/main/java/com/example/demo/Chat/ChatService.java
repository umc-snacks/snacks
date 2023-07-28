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
import com.example.demo.Chat.Dto.ChatRoomDTO.Get;
import com.example.demo.Chat.Dto.ChatRoomListDTO;
import com.example.demo.Chat.Dto.FollowDTO;
import com.example.demo.Chat.Dto.MessageDTO;
import com.example.demo.Chat.Entity.ChatMessage;
import com.example.demo.Chat.Entity.ChatRoom;
import com.example.demo.Chat.Entity.ChatRoomMember;
import com.example.demo.Chat.Entity.ChatRoomMemberId;
import com.example.demo.Chat.Exception.ErrorCode;
import com.example.demo.Chat.Exception.NotFoundException;
import com.example.demo.Chat.repository.ChatMessageRepository;
import com.example.demo.Chat.repository.ChatRoomMemberRepository;
import com.example.demo.Chat.repository.ChatRoomRepository;
import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.profile.domain.follow.FollowRepository;
import com.example.demo.profile.domain.member.Member;
import com.example.demo.profile.domain.member.MemberRepository;

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
	private final FollowRepository followRepository;
	
	/*
	 * 사용자가 속한 채팅방과 팔로워를 모두 조회하여 리턴한다.
	 * 
	 * @param Long memberId 사용자 식별자
	 * @return 사용자가 속한 채팅방 리스트를 DTO로 반환
	 */
	@Transactional(readOnly = true)
	public ChatRoomListDTO getChatList(Long memberId) {
		log.info("--getChatList--");
		
		Member member = verifiedMember(memberId);	// 유효성 검사, memberId에 해당하는 사용자가 없으면 NotFoundException 발생

		List<Object[]> crmAndrt = chatRoomMemberRepository.findMembersWithSameRoomAndReceiveTime(memberId);
		
		//팔로워 목록을 가져오는 코드 
		List<Follow> followList = followRepository.findAllMyFollowr(memberId);
		
		List<FollowDTO> fdtoList = followList.stream()
				.map(s -> Mapper.FollowToDTO(s))
				.collect(Collectors.toList());
		/*
		 * 팀 약속 시간을 가져오는 코드
		 */

		Map<Long, ChatRoomDTO.Get> chatMap = new HashMap<>();

	    for (Object[] list : crmAndrt) {
	        ChatRoomMember crm = (ChatRoomMember) list[0];
	        LocalDateTime time = (LocalDateTime) list[1];
	        String content = (String) list[2];

	        Long roomId = crm.getChatRoom( ).getRoomId();
	        ChatRoomDTO.Get cr = chatMap.get(roomId);

	        if (cr == null) {
	            cr = new ChatRoomDTO.Get();
	            boolean isTeam = crm.getChatRoom().getTeam() != null ? true : false;
	            
	            cr.setType(isTeam ? "team" : "private");
	            cr.setRoomId(roomId);
	            cr.setName(isTeam ? crm.getChatRoom().getTeam().getTeamName() : crm.getMember().getNickname());
	            cr.setNumberOfUnreadMessage(chatMessageRepository.getNumberOfUnreadMessage(memberId, roomId));
	            cr.setContent(content);
	            cr.setSentAt(time);
	            cr.setAppointment(null);	
	            
	            chatMap.put(roomId, cr);
	        }
	    }
	    
	    List<Get> chatRoomList = chatMap.values().stream()
	    		.sorted(Comparator.comparing(ChatRoomDTO.Get::getSentAt).reversed())
	    		.collect(Collectors.toList());
	    
	    return ChatRoomListDTO.builder()
	    		.ChatRoomDTOGetList(chatRoomList)
	    		.followList(fdtoList)
	    		.build();
	}
	
	/*
	 * 팀에 어떤 사용자가 추가되었을 때 채팅방에 해당 사용자를 추가하는 메서드
	 */
	public void addMemberToTeam(Long TeamId, Long memberId) {
		Member member = verifiedMember(memberId);
		ChatRoom chatRoom = chatRoomRepository.findByTeamId(TeamId);
		ChatRoomMemberId chatRoomMemberId = new ChatRoomMemberId(chatRoom.getRoomId(), member.getId());
		
		ChatRoomMember chatRoomMember = ChatRoomMember.builder()
				.chatRoom(chatRoom)
				.chatRoomMemberId(chatRoomMemberId)
				.member(member)
				.readTime(LocalDateTime.now())
				.build();
		
		chatRoomMemberRepository.saveAndFlush(chatRoomMember);
	}
	
	
	/*
	 * 채팅방을 생성하는 메서드, A가 B에게 처음 채팅을 걸려고 할 때
	 * 
	 * @param userName B의 닉네임에 해당
	 * @return chatRoom.getRoomId() 
	 */
	public Long createPrivateChatRoom(Long myMemberId, String theOtherMemberName) {
		// 사용자 닉네임으로 찾는 부분
		Member sender = null;// = new Member(11L, "11");	// 임시
//		MemberEntity receiver = memberRepository.getByNickName(theOtherMemberName);
		Member receiver = null;// = new Member(12L, "12");
		
		ChatRoom chatRoom = chatRoomRepository.saveAndFlush(new ChatRoom());
		
		// sender
		ChatRoomMember chatRoomMemeberSender = ChatRoomMember.builder()
				.chatRoomMemberId(new ChatRoomMemberId(sender.getId(), chatRoom.getRoomId()))
				.chatRoom(chatRoom)
				.member(sender)
				.readTime(LocalDateTime.now())
				.build();
		
		// receiver
		ChatRoomMember chatRoomMemeberReceiver = ChatRoomMember.builder()
				.chatRoomMemberId(new ChatRoomMemberId(receiver.getId(), chatRoom.getRoomId()))
				.chatRoom(chatRoom)
				.member(receiver)
				.readTime(LocalDateTime.now())
				.build();
		
		chatRoomMemberRepository.save(chatRoomMemeberSender);
		chatRoomMemberRepository.save(chatRoomMemeberReceiver);
		
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
		Member memberEntity = verifiedMember(mdto.getSenderId());

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
			MessageDTO.Response mdtor = Mapper.ChatMessageToMessageDtoResponse(cm);
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
	public void setReadingTime(Long memberId, Long roomId) throws NullPointerException {
		log.info("--setReadingTime--");
		
		try {
			ChatRoomMember crm = chatRoomMemberRepository.findByMemberIdAndRoomId(memberId, roomId);
			crm.setReadTime(LocalDateTime.now());
			chatRoomMemberRepository.save(crm);
		} catch (NullPointerException e) {
			throw new NotFoundException(ErrorCode.MEMBER_OR_ROOM_NOT_FOUND);
		}
	}
	
//	public MemberEntity findByMemberName(Long memberId) {
//		log.info("--verifiedMember--");
//		
//		Optional<MemberEntity> optionalMemberEntity = memberRepository.findBy
//	}
	
	/*
	 * 보류, user, user_info, member 정리 될 때까지
	 */
//	public User verifiedUser(Long userId) {
//		User followedUser = userRepository.findById(userId);
//		
//	}
	
	@Cacheable(cacheNames = "members")	// 캐싱이 안되는데 원인을 모르겠음
	public Member verifiedMember(Long memberId) {
		log.info("== verifiedMember ==");
		
		Optional<Member> optionalMemberEntity = memberRepository.findById(memberId);
		return optionalMemberEntity.orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	@Cacheable(cacheNames = "chatRooms")	// 캐싱이 안되는데 원인을 모르겠음
	public ChatRoom verifiedChatRoom(Long roomId) {
		log.info("== verifiedChatRoom ==");
		
		Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(roomId);
		return optionalChatRoom.orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_NOT_FOUND));
	}
}


