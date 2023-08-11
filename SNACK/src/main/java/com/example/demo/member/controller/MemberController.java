package com.example.demo.member.controller;

import com.example.demo.board.dto.BoardResponseDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.member.dto.MemberRequestDTO;
import com.example.demo.member.dto.MemberResponseDTO;
import com.example.demo.member.entity.Member;

import com.example.demo.member.service.MemberService;
import com.example.demo.profile.domain.userinfo.UserInfo;
import com.example.demo.profile.domain.userinfo.UserInfoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
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
        UserInfo emptyUserInfo = new UserInfo(0L, 0L, 0L);

        memberService.save(memberRequestDTO, emptyUserInfo);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);

    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> member) {

        String loginResult = memberService.login(member);

        if (loginResult != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResult);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    //아래 만든게 회원가입할때 id 복수 체크 부분
    @GetMapping("save/{user_id}")
    public ResponseEntity<Boolean> checkIdExist(@PathVariable String user_id) {
        return memberService.findMemberByLoginId(user_id).isPresent() ? ResponseEntity.ok().body(true) : ResponseEntity.ok().body(false);
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

    @GetMapping("{memberLoginId}")
    public ResponseEntity searchMemberId(@PathVariable String memberLoginId, Authentication authentication) {
        Member member = memberService.findMemberByLoginId(memberLoginId);
        Member authMember = memberService.findMemberById(authentication.getName());

        if (member.equals(authMember)) {
            MemberResponseDTO memberResponseDTO = MemberResponseDTO.toResponseEntity(member);
        }

        return ResponseEntity.ok().body(memberResponseDTO);
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






