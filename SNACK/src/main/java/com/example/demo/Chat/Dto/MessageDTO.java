package com.example.demo.Chat.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class MessageDTO {
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Get {
		private String senderName;
		private String content;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		private Long senderId;
		private Long roomId;
		private String content;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		private String senderName;
		private String content;
		private LocalDateTime time;
	}
}
