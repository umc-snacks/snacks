package com.example.demo.Chat.Dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class ChatRoomDTO {
	
	@Getter
	@Setter
	@RequiredArgsConstructor
	public static class Get {
		private String type;
		private Long roomId;
		private String name;	
		private int numberOfUnreadMessage;
		private String imageUri;
		private LocalDateTime time;
	}
}
