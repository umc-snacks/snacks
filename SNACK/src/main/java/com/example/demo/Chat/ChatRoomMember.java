package com.example.demo.Chat;

import com.example.demo.Member.MemberEntity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class ChatRoomMember {
    @EmbeddedId
    private ChatRoomMemberId id;

    @ManyToOne
    @MapsId("chatRoomId")
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private MemberEntity member;
    
    @Builder
    public ChatRoomMember() {}
}
