package com.example.demo.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity(name="member")
@Getter
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_id;
	
	private String nickname;
	
	@Builder
	public MemberEntity() {}
}
