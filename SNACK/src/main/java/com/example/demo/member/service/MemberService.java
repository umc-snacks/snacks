package com.example.demo.member.service;


import com.example.demo.config.JwtTokenProvider;
import com.example.demo.member.dto.MemberDTO;
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
    private final RandomPasswordService randompasswordService;
    private final JwtTokenProvider jwtTokenProvider;

    public void save(MemberDTO memberDTO, UserInfo userInfo) {
        Member member = Member.toMemberEntity(memberDTO, passwordEncoder, userInfo);
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

                return  jwtTokenProvider.createToken(member.getLoginId(), roles);
            }
            else{
                // 비밀번호 불일치!!(로그인 실패!)
                return null;
            }

        }
        return null;

    }

    public String Idfind(MemberDTO memberDTO){
        Optional<Member> findMemberId = memberRepository.findByName(memberDTO.getName());

        String return_id= null ;


        if(findMemberId.isPresent()){
            Member member = findMemberId.get();
            if(member.getBirth().equals((memberDTO.getBirth())) ){
                return_id = member.getLoginId();

            }
        }
        return return_id;
    }



    public Optional<Member> findOne_withID(String insertedUserId) {
        return memberRepository.findByLoginId(insertedUserId);
    }

    public Optional<Member> findOne_withnickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public String changepw(MemberDTO memberDTO) {

        Optional<Member> change_member_pw = memberRepository.findByNameAndLoginIdAndBirth(memberDTO.getName(),memberDTO.getLoginId(),memberDTO.getBirth());
        String change_pw=null;
        if(change_member_pw.isPresent()){


            // 임시비밀번호 부여!!!
            change_pw="1234";
            //아래는 그 랜덤배열
            //change_pw=randompasswordService.getRamdomPassword(8);

            //DB 바꾸기 과정중!!!
            Member member =change_member_pw.get();

            MemberDTO dto = MemberDTO.toMemberDTO_with_LongId(member);
            Member member_with_newpw = Member.toMemberEntity_with_newpw(dto,change_pw, passwordEncoder);
             memberRepository.save(member_with_newpw);


        }

        return change_pw;

    }


}
