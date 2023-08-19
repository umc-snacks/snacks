package com.example.demo.Chat.repository;

import com.example.demo.Chat.Dto.ChatRoomMemberJoinMessageDTO;

import java.util.List;

public interface ChatRoomMemberCustomRepository {
    void deleteMemberToChatRoom(Long memberId, Long crmId);

//    ChatRoom findExistedChatRoom(Long creatorId, Long participantId);

    public List<ChatRoomMemberJoinMessageDTO> getChatRoomMemberJoinChatMessage(Long memberId);
}
