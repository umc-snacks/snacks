package com.example.demo.Chat.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Chat.Entity.ChatRoom;
import com.example.demo.Chat.Entity.ChatRoomMember;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>{
	@Query("SELECT crm, "
			+ "("
			+ "SELECT cm.sentAt"
			+ " FROM ChatMessage cm"
			+ " WHERE cm.chatRoom.roomId = crm.chatRoom.roomId"
			+ " ORDER BY cm.sentAt DESC LIMIT 1"
			+ ") "
			+ ", ("
			+ "SELECT cm.content"
			+ " FROM ChatMessage cm"
			+ " WHERE cm.chatRoom.roomId = crm.chatRoom.roomId"
			+ " ORDER BY cm.sentAt DESC LIMIT 1"
			+ ") "
			+ "FROM ChatRoomMember crm "
			+ "WHERE crm.chatRoom.roomId"
			+ " IN ("
			+ " SELECT crm2.chatRoom.roomId"
			+ " FROM ChatRoomMember crm2"
			+ " WHERE crm2.member.memberLoginId = :memberId"
			+ ")"
			+ " AND crm.member.memberLoginId != :memberId")
	List<Object[]> findMembersWithSameRoomAndReceiveTime(@Param("memberId") Long memberId);
	
	@Query("SELECT crm FROM ChatRoomMember crm "
			+ "JOIN FETCH crm.member "
			+ "WHERE crm.member.memberLoginId = :memberId "
			+ "AND crm.chatRoom.roomId = :roomId")
	ChatRoomMember findByMemberIdAndRoomId(@Param("memberId")Long memberId, @Param("roomId")Long roomId);
	
	@Modifying
	@Query("DELETE FROM ChatRoomMember crm WHERE crm.chatRoom.roomId = :roomId")
	int deleteByRoomId(@Param("roomId") Long roomId);
	
	@Query("SELECT c.readTime FROM ChatRoomMember c " +
            "WHERE c.chatRoom = :chatRoom " +
            "ORDER BY c.readTime ASC " +
            "LIMIT 1")
    LocalDateTime findOldestReadTimeByChatRoom(ChatRoom chatRoom);
}
