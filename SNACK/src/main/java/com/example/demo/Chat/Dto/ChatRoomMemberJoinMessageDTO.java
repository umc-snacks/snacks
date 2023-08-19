package com.example.demo.Chat.Dto;

import com.example.demo.Chat.Entity.ChatRoomMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomMemberJoinMessageDTO {
    private ChatRoomMember chatRoomMember;
//    private List<ChatMessage> chatMessages;
    private String content;
    private LocalDateTime sentAt;
}
