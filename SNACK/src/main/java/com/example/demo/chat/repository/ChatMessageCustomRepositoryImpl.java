package com.example.demo.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import static com.example.demo.chat.Entity.QChatMessage.chatMessage;

@AllArgsConstructor
public class ChatMessageCustomRepositoryImpl implements ChatMessageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteMessagePeriodicallyEvery3Day() {
        jpaQueryFactory.delete(chatMessage)
                .where(chatMessage.sentAt.before(LocalDateTime.now().minusDays(3)));
    }

}
