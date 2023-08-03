package com.example.demo.Chat.Entity;

import com.example.demo.BaseTimeEntity;
import com.example.demo.profile.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
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
    private Member member;
    
    private LocalDateTime readTime;
    
    public void setReadTime(LocalDateTime readTime) {
    	this.readTime = readTime;
    }
}
