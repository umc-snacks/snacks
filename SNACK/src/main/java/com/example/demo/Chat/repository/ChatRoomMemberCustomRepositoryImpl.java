package com.example.demo.Chat.repository;

// Repository에서 queryDSL 사용하는 연습을 하기위해서 생성

import com.example.demo.Chat.Dto.ChatRoomMemberJoinMessageDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.Chat.Entity.QChatMessage.chatMessage;
import static com.example.demo.Chat.Entity.QChatRoomMember.chatRoomMember;

@AllArgsConstructor
@Repository
public class ChatRoomMemberCustomRepositoryImpl implements ChatRoomMemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteMemberToChatRoom(Long memberId, Long crmId) {
        jpaQueryFactory.delete(chatRoomMember)
                .where(chatRoomMember.member.id.eq(memberId)
                        .and(chatRoomMember.chatRoom.roomId.eq(crmId)))
                .execute();

    }

//    @Override
//    public List<ChatRoomMemberJoinMessageDTO> getChatRoomMemberJoinChatMessage(Long memberId) {
//        return jpaQueryFactory
//                .select(Projections.fields(
//                        ChatRoomMemberJoinMessageDTO.class,
//                        chatRoomMember,
//                        chatMessage.content,
//                        chatMessage.sentAt
//                ))
//                .from(chatRoomMember)
//                .join(chatMessage).on(chatRoomMember.chatRoom.eq(chatMessage.chatRoom))
//                .where(chatRoomMember.readTime.before(chatMessage.sentAt)
//                        .and(chatRoomMember.member.id.eq(memberId)))
//                .fetch();
//    }

    @Override
    public List<ChatRoomMemberJoinMessageDTO> getChatRoomMemberJoinChatMessage(Long memberId) {
        return jpaQueryFactory
                .select(Projections.fields(ChatRoomMemberJoinMessageDTO.class,
                        chatRoomMember.as("chatRoomMember"),
//                        chatMessage.as("chatMessages")
                        chatMessage.content.as("content"),
                        chatMessage.sentAt.as("sentAt")
                ))
                .from(chatRoomMember)
                .leftJoin(chatMessage).on(chatRoomMember.chatRoom.roomId.eq(chatMessage.chatRoom.roomId))
                .where(chatRoomMember.chatRoom.roomId.in(
                                JPAExpressions.select(chatRoomMember.chatRoom.roomId)
                                        .from(chatRoomMember)
                                        .where(chatRoomMember.member.id.eq(memberId)))
                        .and(chatRoomMember.member.id.ne(memberId))
                )
                .groupBy(chatRoomMember)
                .fetch();
    }

//    /*
//     * 사용자와 상대방의 채팅방이 있는지 확인하는 쿼리 있다면 ChatRoom 반환
//     */
//    @Override
//    public ChatRoom findExistedChatRoom(Long creatorId, Long participantId) {
//        List<Long> ids = Arrays.asList(creatorId, participantId);
//
//        return (ChatRoom) jpaQueryFactory
//                .selectFrom(chatRoom)
//                .join(chatRoomMember.chatRoom, chatRoom)
//                .where(chatRoomMember.member.id.in(ids))
//                .fetch();
//    }
}

