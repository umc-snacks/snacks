package com.example.demo.Chat.Entity;

import com.example.demo.BaseTimeEntity;
import com.example.demo.entity.MemberEntity;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("chatRoomId")
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("memberId")
    private MemberEntity member;
    
    private LocalDateTime readTime;
    
    public void setReadTime(LocalDateTime readTime) {
    	this.readTime = readTime;
    }
}
