package com.example.demo.Member;

import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, BoardRepository boardRepository, BoardMemberRepository boardMemberRepository) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.boardMemberRepository = boardMemberRepository;
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

    public List<Board> findBoardsByMemberId(Long memberId){
        return boardMemberRepository.searchAttendingBoardsByMemberId(memberId);
    }
}
