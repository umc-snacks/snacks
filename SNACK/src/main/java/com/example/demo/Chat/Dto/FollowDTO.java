package com.example.demo.Chat.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class FollowDTO {
	private String nickName;
	private Long id;
}
