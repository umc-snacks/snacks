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
}

