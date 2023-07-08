package com.example.demo.Chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Chat.ChatRoomMember;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>{
	@Query("SELECT crm FROM ChatRoomMember crm "
			+ "WHERE crm.chatRoom.id IN ("
				+ "SELECT crm2.chatRoom.id FROM ChatRoomMember crm2 "
				+ "WHERE crm2.member.id = :member_id)"
			+ "AND crm.member.id != :member_id")
	List<ChatRoomMember> findMembersWithSameRoom(@Param("member_id") Long member_id);
}
