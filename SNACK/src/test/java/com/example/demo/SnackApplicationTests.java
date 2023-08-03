package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Chat.repository.ChatMessageRepository;
import com.example.demo.Chat.repository.ChatRoomMemberRepository;
import com.example.demo.Chat.repository.ChatRoomRepository;
import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.profile.domain.follow.FollowRepository;
import com.example.demo.profile.domain.member.Member;
import com.example.demo.profile.domain.member.MemberRepository;

import jakarta.transaction.Transactional;

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

//	@DisplayName("멤버 추가")
//	@Test
//	void contextLoads() {
//		for(int i = 0; i < 100; i++) {
//			// (String username, String nickname, String password, UserInfo userInfo)
//			Member member = new Member("member" + i, "mNickName" + i, "123" + i, new UserInfo(0L, 0L, 0L));
//			memberRepository.save(member);
//		}
//	}
	
//	@Test
//	void addFollowr() {
//		List<Member> list = memberRepository.findAll();
//		Member member = list.get(0);
//		
//		
//		for(int i = 1; i < 100; i++) {
//			Follow f = new Follow(list.get(i), member);
//			followRepository.save(f);
//			
//			if(i % 2 == 0) {
//				followRepository.save(new Follow(member, list.get(i)));
//			}
//			
//		}
//	}
	
	@Test
	@Transactional
	void test() {
		Optional<Member> m = memberRepository.findById(1L);
		List<Follow> fList = followRepository.findAllMyFollowr(m.get().getId());
		List<Object[]> crmAndrt = chatRoomMemberRepository.findMembersWithSameRoomAndReceiveTime(m.get().getId());
		
		for(int i = 0; i < fList.size(); i++) {
			System.out.println(crmAndrt.get(i));
//			System.out.println(fList.get(i).getFollower().getNickname());
		}
	}

}
