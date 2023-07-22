package com.example.demo.config;

import lombok.Getter;

@Getter
public enum CacheType {
	// 캐시 이름, expired 시간, 캐시 길이
	MEMBERS("members", 60, 10000), 
	CHATROOMS("chatRooms", 60, 10000);

	CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
		this.cacheName = cacheName;
		this.expiredAfterWrite = expiredAfterWrite;
		this.maximumSize = maximumSize;
	}

	private String cacheName;
	private int expiredAfterWrite;
	private int maximumSize;
}
