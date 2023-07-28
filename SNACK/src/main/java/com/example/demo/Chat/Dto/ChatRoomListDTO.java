package com.example.demo.Chat.Dto;

import java.util.List;

import com.example.demo.profile.dto.profileUpdate.ProfileReadResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ChatRoomListDTO {
	private List<ChatRoomDTO.Get> ChatRoomDTOGetList;
	private List<FollowDTO> followList;
}
