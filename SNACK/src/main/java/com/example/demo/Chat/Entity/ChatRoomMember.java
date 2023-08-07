package com.example.demo.Chat.Entity;

import com.example.demo.BaseTimeEntity;
import com.example.demo.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomMember extends BaseTimeEntity {
    @EmbeddedId
    private ChatRoomMemberId chatRoomMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chatRoomId")
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;
    
    private LocalDateTime readTime;
    
    public void setReadTime(LocalDateTime readTime) {
    	this.readTime = readTime;
    }
}
