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

    public void saveMember(MemberRequestDTO memberRequestDTO) {
        Member member = Member.builder()
                .name(memberRequestDTO.getName())
                .nickname(memberRequestDTO.getNickname())
                .email(memberRequestDTO.getEmail())
                .build();

        memberRepository.save(member);
    }

    public MemberResponseDTO findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 멤버가 존재하지 않습니다."));

        return MemberResponseDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .memberBoards(member.getMemberBoards())
                .build();
    }
}
