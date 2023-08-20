package com.example.demo.chat.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ChatRoomListDTO {
	private List<ChatRoomDTO.Get> ChatRoomDTOGetList;
	private List<MemberSearchDTO> memberList;
}
