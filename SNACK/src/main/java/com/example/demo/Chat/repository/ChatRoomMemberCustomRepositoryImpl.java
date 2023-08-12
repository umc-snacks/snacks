package com.example.demo.Chat.repository;

// Repository에서 queryDSL 사용하는 연습을 하기위해서 생성

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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

