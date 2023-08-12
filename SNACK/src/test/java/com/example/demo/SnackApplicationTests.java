package com.example.demo;

import com.example.demo.Chat.repository.ChatMessageRepository;
import com.example.demo.Chat.repository.ChatRoomMemberRepository;
import com.example.demo.Chat.repository.ChatRoomRepository;
import com.example.demo.board.BoardService;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.profile.domain.follow.Follow;
import com.example.demo.profile.domain.follow.FollowRepository;
import com.example.demo.profile.domain.userinfo.UserInfo;
import com.example.demo.profile.domain.userinfo.UserInfoRepository;
import com.example.demo.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

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
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardMemberRepository boardMemberRepository;
    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("멤버 추가")
    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
            // (String username, String nickname, String password, UserInfo userInfo)
            Member member = Member.builder()
                    .loginId("test" + i)
                    .pw("1234")
                    .name("test" + i)
                    .nickname("test" + i)
                    .birth(LocalDate.now())
                    .userInfo(null)
                    .profileImageUrl(null)
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
        List<Member> list = memberRepository.findAll();
        Member member = list.get(0);


        for (int i = 1; i < 10; i++) {
            Follow f = new Follow(member, list.get(i));
            followRepository.save(f);

            if (i % 2 == 0) {
                followRepository.save(new Follow(member, list.get(i)));
            }

        }
    }
//    @Test
//    @Transactional
//    void deleteBoardTest() {
//        boardRepository.deleteById(252L);
//    }



//    @Test
//    @Transactional
//    void addBoard() {
//
//        List<Member> list = memberRepository.findAll();
//
//
//        Board board = new Board();
//        board.setTitle("test Title ");
//        board.setGameTitle(Games.valueOf("LeagueOfLegends"));
//        boardRepository.saveAndFlush(board);
//
//        for (int i = 1; i < 4; i++) {
//            Member member = list.get(i);
//            BoardMember bm = BoardMember.builder()
//                    .member(member)
//                    .board(board)
//                    .build();
//
//            boardMemberRepository.saveAndFlush(bm);
//        }
//    }
//
}
