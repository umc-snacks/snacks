package com.example.demo.Chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Chat.Entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
	@Query("SELECT COUNT(*) "
			+ "FROM ChatMessage cm "
			+ "WHERE cm.chatRoom.roomId = :chatRoomId "
			+ "AND cm.sentAt > ("
				+ "SELECT crm.readTime "
				+ "FROM ChatRoomMember crm "
				+ "WHERE crm.member.Id = :myMemberId "
				+ "ORDER BY crm.readTime DESC "
				+ "LIMIT 1)"
			+ "AND cm.sender.Id != :myMemberId")
	int getNumberOfUnreadMessage(@Param("myMemberId") Long myMemberId, @Param("chatRoomId") Long chatRoomId);
	

	@Query("SELECT cm FROM ChatMessage cm "
            + "JOIN FETCH ChatRoomMember crm "
            + "ON crm.member.Id = :memberId "
            + "WHERE cm.chatRoom.roomId = :chatRoomId "
            + "AND cm.sentAt >= (SELECT crm2.readTime FROM ChatRoomMember crm2 "
            					+ "WHERE crm2.member.Id = :memberId "
            					+ "AND crm2.chatRoom.roomId = :chatRoomId) "
            + "AND cm.sender.Id != :memberId")
    List<ChatMessage> getUnreadMessages(@Param("memberId")Long memberId, @Param("chatRoomId")Long chatRoomId);
	
	@Modifying
	@Query("DELETE FROM ChatMessage cm WHERE cm.chatRoom.roomId = :roomId")
	int deleteByRoomId(@Param("roomId") Long roomId);
}
