package com.example.demo;

import com.example.demo.Chat.Entity.ChatRoom;
import com.example.demo.Chat.Entity.ChatRoomMember;
import com.example.demo.Chat.repository.ChatMessageRepository;
import com.example.demo.Chat.repository.ChatRoomMemberRepository;
import com.example.demo.Chat.repository.ChatRoomRepository;
import com.example.demo.entity.MemberEntity;
import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.profile.domain.follow.FollowRepository;
import com.example.demo.profile.domain.userinfo.UserInfo;
import com.example.demo.profile.domain.userinfo.UserInfoRepository;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SnackApplicationTests {
	@Autowired
	private MemberRepository memberRepository; // 나중에 멤버 서비스의 메소드로 대체할 예정
	@Autowired
	private ChatRoomMemberRepository chatRoomMemberRepository;
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@DisplayName("멤버 추가")
	@Test
	void contextLoads() {
		for (int i = 0; i < 10; i++) {
			// (String username, String nickname, String password, UserInfo userInfo)
			MemberEntity member = MemberEntity.builder()
					.id("test" + i)
					.pw("1234")
					.name("test" + i)
					.nickname("test" + i)
					.birth(LocalDate.now())
					.userInfo(null)
					.profileimageurl(null)
					.build();

			UserInfo userInfo = UserInfo.builder()
					.member(member)
					.articleCount(0L)
					.followCount(0L)
					.followerCount(0L)
					.introduction("test hi" + i)
					.build();

			memberRepository.save(member);
			userInfoRepository.save(userInfo);
		}
	}

	@Test
	void addFollowr() {
		List<MemberEntity> list = memberRepository.findAll();
		MemberEntity member = list.get(0);


		for(int i = 1; i < 10; i++) {
			Follow f = new Follow(member, list.get(i));
			followRepository.save(f);

			if(i % 2 == 0) {
				followRepository.save(new Follow(member, list.get(i)));
			}

		}
	}

//    @Test
//    @Transactional
//    void addChatMessage() {
////        List<ChatRoom> crlist = chatRoomRepository.findAll();
////        List<MemberEntity> mlsit = memberRepository.findAll();
//        List<ChatRoomMember> crm = chatRoomMemberRepository.findAll();
//
//        for(int i = 0 ; i < 10; i++) {
//
//        }
//    }
//
}
