package com.example.demo.Chat.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
public class MessageDTO {
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Get {
		@NotBlank
		private String senderName;
		@NotNull
		private String content;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		@Positive
		private Long senderId;
		@Positive
		private Long roomId;
		@NotNull
		private String content;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		private Long memberId;
		private String senderName;
		private String content;
		private LocalDateTime time;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Preview {
		private String content;
		private LocalDateTime time;
	}
}
