package com.example.demo.chat.repository;

import com.example.demo.chat.Dto.ChatRoomMemberJoinMessageDTO;

import java.util.List;

public interface ChatRoomMemberCustomRepository {
    void deleteMemberToChatRoom(Long memberId, Long crmId);

//    ChatRoom findExistedChatRoom(Long creatorId, Long participantId);

    public List<ChatRoomMemberJoinMessageDTO> getChatRoomMemberJoinChatMessage(Long memberId);
}
