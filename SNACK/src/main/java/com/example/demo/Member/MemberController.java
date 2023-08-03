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
    public ResponseEntity<MemberResponseDTO> create(@Valid @RequestBody MemberRequestDTO memberRequestDTO){
        Member member = memberService.saveMember(memberRequestDTO);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.toResponseEntity(member);
        return ResponseEntity.ok().body(memberResponseDTO);
    }

    @GetMapping("{memberId}")
    public ResponseEntity<MemberResponseDTO> read(@PathVariable Long memberId) {
        Member member = memberService.findMember(memberId);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.toResponseEntity(member);
        return ResponseEntity.ok().body(memberResponseDTO);
    }

}
