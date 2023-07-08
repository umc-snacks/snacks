package com.example.demo.Chat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Chat.repository.ChatMessageRepository;
import com.example.demo.Chat.repository.ChatRoomMemberRepository;
import com.example.demo.Member.MemberEntity;
import com.example.demo.Member.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatService {
	private final ComponentService componentService;
	
	/*
	 * 사용자 정보를 받아서 사용자의 채팅 내역을 리턴해주는 메서드
	 * 
	 * @param 정해야함
	 * 
	 * @return Exception
	 * 
	 */
	public List<ChatRoomDTO.Get> getChatList(Long member_id) {
		return componentService.getChatList(member_id);
	}
}

@Service
@AllArgsConstructor
class ComponentService {
	private final MemberRepository memberRepository;
	private final ChatRoomMemberRepository chatRoomMemberRepository;
	private final ChatMessageRepository chatMessageRepository;

	/*
	 * 사용자가 속한 채팅방을 모두 조회하여 리턴한다.
	 * 
	 * @param Long member_id 사용자 식별자
	 * 
	 * @return 사용자가 속한 채팅방 리스트를 DTO로 반환 Exception 사용자가 검색되지 않을 경우 ChatException을
	 * 발생시킴
	 */
	public List<ChatRoomDTO.Get> getChatList(Long member_id) {
		MemberEntity memberEntity = verifiedMember(member_id);
		
		List<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findMembersWithSameRoom(member_id);
		List<ChatRoomDTO.Get> chatList = new ArrayList<>();
		
		for(ChatRoomMember crm : chatRoomMember) {
			ChatRoomDTO.Get cr = new ChatRoomDTO.Get();
			
			cr.setRoom_id(crm.getChatRoom().getRoom_id());
			cr.setMember_id(crm.getMember().getMember_id());
			cr.setName(crm.getMember().getNickname());
			// 성능이 떨어질 것 같은데, 어떻게 해야할지 모르겠음 DB에서 최대한 쿼리를 쳐서 가져와야 하는데, 
			// 각 room 마다 쿼리를 하나 지금 이 방식이나 비슷할 것 같음(지금 생각으로는),, 나중에 리팩토링 해야할 것 같음
			cr.setMessage_count(chatMessageRepository.getUnReadMessageCount(crm.getChatRoom().getRoom_id()));	
			cr.setRoom_id(crm.getChatRoom().getRoom_id());
			
			chatList.add(cr);
		}
		
		// 질문할 것, 정렬의 기준은 가장 최근의 메시지 중 안 읽은 메시지인지?
		return chatList.stream().sorted(Comparator.comparing(ChatRoomDTO.Get::getMessage_count).reversed()).collect(Collectors.toList());
	}

	/*
	 * 사용자가 DB에 존재하는지 확인하는 메서드 
	 */
	public MemberEntity verifiedMember(Long member_id) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(member_id);
		return optionalMemberEntity.orElseThrow(() -> new ChatException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
