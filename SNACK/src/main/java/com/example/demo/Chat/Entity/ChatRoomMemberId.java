package com.example.demo.Chat.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

@Embeddable
public class ChatRoomMemberId implements Serializable {
//	@Column(name="room_id")
	private Long chatRoomId;
	
//	@Column(name="member_id")
    private Long memberId;
    
    public ChatRoomMemberId() {
        // 기본 생성자 필요
    }

    public ChatRoomMemberId(Long chatRoomId, Long memberId) {
        this.chatRoomId = chatRoomId;
        this.memberId = memberId;
    }
}
