package com.example.demo.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Chat.repository.ChatRoomMemberRepository;
import com.example.demo.Member.MemberEntity;
import com.example.demo.Member.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatService {

	/*
	 * 사용자 정보를 받아서 사용자의 채팅 내역을 리턴해주는 메서드
	 * 
	 * @param 정해야함
	 * 
	 * @return Exception
	 * 
	 */
	public List<ChatRoomDTO.Get> getChatList(Long member_id) {

		return null;

	}
}

@Service
@AllArgsConstructor
class ComponentService {
	private final MemberRepository memberRepository;
	private final ChatRoomMemberRepository chatRoomMemberRepository;

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
		List<ChatRoomDTO.Get> list = new ArrayList<>();
		
		for(ChatRoomMember crm : chatRoomMember) {
			ChatRoomDTO.Get cr = new ChatRoomDTO.Get();
			
			cr.setRoom_id(crm.getChatRoom().getRoom_id());
			cr.setMember_id(crm.getMember().getMember_id());
			cr.setName(crm.getMember().getNickname());
			cr.setMessage_count(0);
			cr.setRoom_id(null);
			
			list.add(cr);
		}
		
		return null;
	}

	/*
	 * 사용자가 DB에 존재하는지 확인하는 메서드
	 */
	public MemberEntity verifiedMember(Long member_id) {
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(member_id);
		return optionalMemberEntity.orElseThrow(() -> new ChatException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
