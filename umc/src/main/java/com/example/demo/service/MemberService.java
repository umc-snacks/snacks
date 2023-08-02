package com.example.demo.service;


import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.MemberEntity;
import com.example.demo.profile.domain.UserInfo;
import com.example.demo.repository.MemberRepository;
import com.example.demo.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RandomPasswordService randompasswordService;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;


    //08.03 원본태연코드
   /* public void save(MemberDTO memberDTO) {
        //MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO, passwordEncoder);
        // toMemberEntity_login
        MemberEntity memberEntity = MemberEntity.toMemberEntity_login(memberDTO, passwordEncoder);
       //원본
        memberRepository.save(memberEntity);


    }*/

    //08.03 창민-태연코드
    public void save(MemberDTO memberDTO, UserInfo userInfo) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO, passwordEncoder, userInfo);
        memberRepository.save(memberEntity);
    }
    
    

   /* public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberId = memberRepository.findById(memberDTO.getId());

        if(byMemberId.isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            MemberEntity memberEntity =byMemberId.get();
            if(encoder.matches(memberDTO.getPw(), memberEntity.getPw())){
                // 비밀번호 일치!
                // 처음에 시작을 MemberDTO 라 했으니! entity -> dto 로 변환 후 리턴하는 과정이 필요!!
                MemberDTO dto = MemberDTO.toMemberDTO_after_login(memberEntity);
                System.out.println("현재 MemberService의 login 의 if 첫문장 즉 로그인 성공");
                System.out.println("dto 확인"+dto);
                return  dto;
            }
            else{
                // 비밀번호 불일치!!(로그인 실패!)
                System.out.println(memberEntity);
                System.out.println("현재 MemberService의 login 의 else  즉 로그인 실패");
                return null;
            }

        }
        return null;

    }*/


    // 27일 도전중 - 유투브버전
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    private Long expiredMs = 1000*60*60l;
//    public String login(MemberDTO memberDTO) {
//        Optional<MemberEntity> byMemberId = memberRepository.findById(memberDTO.getId());
//        return JwtUtil.createJwt(memberDTO.getId(),secretKey, expiredMs);
//
//    }

    public String login(Map<String, String> members) {
        Optional<MemberEntity> byMemberId = memberRepository.findById(members.get("id"));

        if(byMemberId.isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            MemberEntity memberEntity =byMemberId.get();
            if(encoder.matches(members.get("pw"), memberEntity.getPw())){
                // 비밀번호 일치!
                // 처음에 시작을 MemberDTO 라 했으니! entity -> dto 로 변환 후 리턴하는 과정이 필요!!
                MemberDTO dto = MemberDTO.toMemberDTO_after_login(memberEntity);
                System.out.println("현재 MemberService의 login 의 if 첫문장 즉 로그인 성공");
                System.out.println("dto 확인"+dto);
                List<String> roles = new ArrayList<>();
                roles.add("user");
                System.out.println("여기안에서 jwtToken확인~~");
                System.out.println(jwtTokenProvider.createToken(memberEntity.getId(), roles));
                System.out.println("~~~~~~~~~~~~~~");

                return  jwtTokenProvider.createToken(memberEntity.getId(), roles);
            }
            else{
                // 비밀번호 불일치!!(로그인 실패!)
                System.out.println(memberEntity);
                System.out.println("현재 MemberService의 login 의 else  즉 로그인 실패");
                return null;
            }

        }
        return null;

    }

    public String login_find_getUserPk(String word) {

        return jwtTokenProvider.getUserPk(word);
    }



    public String Idfind(MemberDTO memberDTO){
        Optional<MemberEntity> findMemberId = memberRepository.findByName(memberDTO.getName());

        String return_id= null ;


        if(findMemberId.isPresent()){
            //System.out.println("확인111");
            MemberEntity memberEntity = findMemberId.get();
            if(memberEntity.getBirth().equals((memberDTO.getBirth())) ){
                //System.out.println("확인222");
                return_id = memberEntity.getId();

            }
        }

        //System.out.println("return_id 값 : "+return_id);
        return return_id;
    }



    public Optional<MemberEntity> findOne_withID(String insertedUserId) {
        return memberRepository.findById(insertedUserId);
    }

    public Optional<MemberEntity> findOne_withnickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public String changepw(MemberDTO memberDTO) {

        Optional<MemberEntity> change_member_pw = memberRepository.findByNameAndIdAndBirth(memberDTO.getName(),memberDTO.getId(),memberDTO.getBirth());
        String change_pw=null;
        if(change_member_pw.isPresent()){


            // 임시비밀번호 부여!!!
            change_pw="1234";
            //아래는 그 랜덤배열
            //change_pw=randompasswordService.getRamdomPassword(8);

            //DB 바꾸기 과정중!!!
            MemberEntity memberEntity =change_member_pw.get();

            MemberDTO dto = MemberDTO.toMemberDTO_with_LongId(memberEntity);
            System.out.println(dto);
            MemberEntity memberEntity_with_newpw = MemberEntity.toMemberEntity_with_newpw(dto,change_pw, passwordEncoder);
             memberRepository.save(memberEntity_with_newpw);

            System.out.println("이름,아이디,생년월일을 이용해 찾기 성공! 그후 db 바꾸기 과정도 수행");

        }
        else{
            System.out.println("이름,아이디,생년월일을 이용해 찾기 실패!");
        }

        return change_pw;




    }


}
