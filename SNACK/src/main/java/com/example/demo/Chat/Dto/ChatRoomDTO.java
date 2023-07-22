package com.example.demo.Chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class ChatRoomDTO {
	
	@Getter
	@Setter
	@RequiredArgsConstructor
	public static class Get {
		private Long room_id;
		private Long member_id;	
		private String name;	
		private int message_count;
		private String image_uri;
	}
}
