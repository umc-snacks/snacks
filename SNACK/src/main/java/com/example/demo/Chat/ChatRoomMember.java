package com.example.demo.Chat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.example.demo.Member.MemberEntity;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class ChatRoomMember {
    @EmbeddedId
    private ChatRoomMemberId id;

    @ManyToOne
    @MapsId("chatRoomId")
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
