package com.example.demo.chat.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MemberSearchDTO {
	private String nickName;
	private Long memberId;
}
