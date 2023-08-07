package com.example.demo.Chat.repository;

public interface ChatRoomMemberCustomRepository {
    void deleteMemberToChatRoom(Long memberId, Long crmId);
}
