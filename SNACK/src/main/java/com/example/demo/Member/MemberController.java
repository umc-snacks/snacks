package com.example.demo.Member;

import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberResponseDTO;
import com.example.demo.socialboard.dto.SocialBoardDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody MemberRequestDTO memberRequestDTO){
        memberService.saveMember(memberRequestDTO);
        return ResponseEntity.ok().body(memberRequestDTO);
    }

    @GetMapping("{memberId}")
    public ResponseEntity<MemberResponseDTO> read(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findMember(memberId));
    }

}
