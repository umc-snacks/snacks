package com.example.demo.Member;

import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember(MemberRequestDTO memberRequestDTO) {
        Member member = Member.builder()
                .name(memberRequestDTO.getName())
                .nickname(memberRequestDTO.getNickname())
                .email(memberRequestDTO.getEmail())
                .build();

        memberRepository.save(member);
        return member;
    }

    public Member findMember(Long memberId) {

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 멤버가 존재하지 않습니다."));
    }
}
