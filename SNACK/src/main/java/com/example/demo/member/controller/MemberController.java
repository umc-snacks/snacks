package com.example.demo.member.controller;

import com.example.demo.board.dto.BoardResponseDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.member.dto.MemberRequestDTO;
import com.example.demo.member.entity.Member;

import com.example.demo.member.service.MemberService;
import com.example.demo.profile.domain.userinfo.UserInfo;
import com.example.demo.profile.domain.userinfo.UserInfoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {

    private final MemberService memberService;
    private final UserInfoRepository userInfoRepository;

    // 회원가입
    @PostMapping("save")
    public ResponseEntity<Boolean> save(@RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        ///창민님 코드
        // 회원가입할때

        UserInfo emptyUserInfo = new UserInfo(0L, 0L, 0L);
        memberService.save(memberRequestDTO, emptyUserInfo);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
        // 반납할때는 true값보다 생성된 회원 id를 보내주는게 맞지않을까요?

    }

    @AllArgsConstructor
    @Getter
    @Setter
    class Token{
        private String token;
    }

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestBody  Map<String, String> member) {
        String loginResult = memberService.login(member);

        if (loginResult != null) {
            Token token = new Token(loginResult);
            return ResponseEntity.ok().body(token);
        } else {
            Token tokenfalse = new Token(null);

            return ResponseEntity.ok().body(tokenfalse);

        }

    }

    //아래 만든게 회원가입할때 id 복수 체크 부분
    @GetMapping("/save/{user_id}")
    public ResponseEntity<Boolean> checkIdExist(@PathVariable String user_id) {
        Member member = memberService.findMemberByLoginId(user_id).orElse(null);

        return member != null ? ResponseEntity.ok().body(true) : ResponseEntity.ok().body(false);
    }


    // TODO 테스트 필요
    @GetMapping("attending/{memberId}")
    public ResponseEntity<List> searchAttendingBoards(@PathVariable String memberId, Authentication authentication) {
        // authentication  멤버 식별 번호
        String name = authentication.getName();

        List<Board> boards = memberService.findBoardsByMemberId(memberId);
        List<BoardResponseDTO> responseDTOList = boards.stream()
                .map(BoardResponseDTO::getBuild)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOList);

    }
//
//    // 아이디찾기
//    // 일단은 이름과 생년월일, 이메일으로 -> 아이디 찾기 진행함!
//    @GetMapping("/idSearch")
//    public String UserIdSearch(@RequestBody MemberRequestDTO memberRequestDTO){
//        return memberService.Idfind(memberRequestDTO);
//    }
//
//    @GetMapping("/email/{send_email}")
//    public String temp(@RequestBody MemberRequestDTO memberRequestDTO, @PathVariable String send_email){
//
//        String changepw = memberService.changepw(memberRequestDTO);
//
//        EmailService.send(send_email,changepw);
//        return "성공";
//
//    }
}






