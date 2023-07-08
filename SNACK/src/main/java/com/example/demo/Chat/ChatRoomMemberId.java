package com.example.demo.Chat;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ChatRoomMemberId implements Serializable {
    private Long chatRoomId;
    private Long memberId;
}
