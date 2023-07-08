package com.example.demo.Chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Chat.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>{
	@Query("SELECT crm FROM ChatRoomMember crm WHERE crm.chatRoom.id = ("
				+ "SELECT crm2.chatRoom.id FROM ChatRoomMember crm2 "
				+ "WHERE crm2.member.id = :memberId)")
	List<ChatRoomMember> findMembersWithSameRoom(@Param("memberId") Long memberId);
}
