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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor// 이게 있어야 MemberRepository 성공!
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardMemberRepository boardMemberRepository;

    public void save(MemberRequestDTO memberRequestDTO, UserInfo userInfo) {
        Member member = Member.toMemberEntity(memberRequestDTO, passwordEncoder, userInfo);
        memberRepository.save(member);
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

    public Optional<Member> findMemberByLoginId(String insertedUserId) {
        return memberRepository.findByLoginId(insertedUserId);
    }

    public List<Board> findBoardsByMemberId(String memberId){
        return boardMemberRepository.searchAttendingBoardsByMemberId(Long.parseLong(memberId));
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
