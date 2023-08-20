package com.example.demo.chat.repository;

import com.example.demo.chat.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageCustomRepository {
	@Query("SELECT COUNT(*) "
			+ "FROM ChatMessage cm "
			+ "WHERE cm.chatRoom.roomId = :chatRoomId "
			+ "AND cm.sentAt > ("
				+ "SELECT crm.readTime "
				+ "FROM ChatRoomMember crm "
				+ "WHERE crm.member.id = :myMemberId "
				+ "ORDER BY crm.readTime DESC "
				+ "LIMIT 1)"
			+ "AND cm.sender.id != :myMemberId")
	int getNumberOfUnreadMessage(@Param("myMemberId") Long myMemberId, @Param("chatRoomId") Long chatRoomId);
	

	@Query("SELECT cm FROM ChatMessage cm "
            + "JOIN FETCH ChatRoomMember crm "
            + "ON crm.member.id = :memberId "
            + "WHERE cm.chatRoom.roomId = :chatRoomId "
            + "AND cm.sentAt >= (SELECT crm2.readTime FROM ChatRoomMember crm2 "
            					+ "WHERE crm2.member.id = :memberId "
            					+ "AND crm2.chatRoom.roomId = :chatRoomId) "
            + "AND cm.sender.id != :memberId")
    List<ChatMessage> getUnreadMessages(@Param("memberId")Long memberId, @Param("chatRoomId")Long chatRoomId);
	
	@Modifying
	@Query("DELETE FROM ChatMessage cm WHERE cm.chatRoom.roomId = :roomId")
	int deleteByRoomId(@Param("roomId") Long roomId);
}
