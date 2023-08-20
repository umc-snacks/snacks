package com.example.demo.chat.Entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ChatRoomMemberId implements Serializable {
	private Long chatRoomId;
	
    private Long memberId;
    
    public ChatRoomMemberId() {
        // 기본 생성자 필요
    }

    public ChatRoomMemberId(Long chatRoomId, Long memberId) {
        this.chatRoomId = chatRoomId;
        this.memberId = memberId;
    }
}
