package com.example.demo.member.service;

import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.config.JwtTokenProvider;
import com.example.demo.member.dto.MemberRequestDTO;
import com.example.demo.member.entity.Member;
import com.example.demo.profile.domain.userinfo.UserInfo;
import com.example.demo.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor// 이게 있어야 MemberRepository 성공!
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardMemberRepository boardMemberRepository;

    public void save(MemberRequestDTO memberRequestDTO, UserInfo userInfo) {
        validateMemberRequest(memberRequestDTO);
        Member member = Member.toMemberEntity(memberRequestDTO, passwordEncoder, userInfo);
        memberRepository.save(member);
    }

        private void validateMemberRequest(MemberRequestDTO memberRequestDTO) {
            boolean loginIdFlag = memberRepository.findByLoginId(memberRequestDTO.getPw()).isPresent();
            boolean nicknameFlag = memberRepository.findByNickname(memberRequestDTO.getNickname()).isPresent();

            if (loginIdFlag || nicknameFlag) {
                throw new IllegalArgumentException("중복발생");
            }
        }


    public String login(Map<String, String> members) {
        Optional<Member> byMemberId = memberRepository.findByLoginId(members.get("loginId"));

        if(byMemberId.isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            Member member =byMemberId.get();
            if(encoder.matches(members.get("pw"), member.getPw())){
                // 비밀번호 일치!
                // 처음에 시작을 MemberDTO 라 했으니! entity -> dto 로 변환 후 리턴하는 과정이 필요!!
                List<String> roles = new ArrayList<>();
                roles.add("user");

                return jwtTokenProvider.createToken(member.getId().toString(), roles);
            }
            else{
                // 비밀번호 불일치!!(로그인 실패!)
                return null;
            }

        }
        return null;

    }

    public Member findMemberByLoginId(String insertedUserId) {
        return memberRepository.findByLoginId(insertedUserId)
                .orElseThrow(() -> new NoSuchElementException("Id를 찾을 수 없습니다."));
    }

    public List<Board> findBoardsByMemberId(Long memberId){
        return boardMemberRepository.searchAttendingBoardsByMemberId(memberId);
    }

    public Member findMemberById(String memberId) {
        Long parseId = Long.parseLong(memberId);
        return memberRepository.findById(parseId)
                .orElseThrow(() -> new NoSuchElementException("Id를 찾을 수 없습니다."));
    }

    public String changePassword(MemberRequestDTO memberRequestDTO) {

        Optional<Member> change_member_pw = memberRepository.findByNameAndLoginIdAndBirth(memberRequestDTO.getName(), memberRequestDTO.getLoginId(), memberRequestDTO.getBirth());
        String change_pw=null;
        if(change_member_pw.isPresent()){


            // 임시비밀번호 부여!!!
            change_pw="1234";
            //아래는 그 랜덤배열
            //change_pw=randompasswordService.getRamdomPassword(8);

            //DB 바꾸기 과정중!!!
            Member member =change_member_pw.get();

            MemberRequestDTO dto = MemberRequestDTO.toMemberDTOWithLongId(member);
            Member member_with_newpw = Member.toMemberEntityWithNewpw(dto,change_pw, passwordEncoder);
             memberRepository.save(member_with_newpw);

        }

        return change_pw;

    }



}
