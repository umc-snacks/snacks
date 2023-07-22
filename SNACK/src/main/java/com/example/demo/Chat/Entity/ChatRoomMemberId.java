package com.example.demo.Chat.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ChatRoomMemberId implements Serializable {
//	@Column(name="room_id")
	private Long chatRoomId;
	
//	@Column(name="member_id")
    private Long memberId;
}
