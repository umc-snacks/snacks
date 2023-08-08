package com.example.demo.member.controller;

//import com.example.demo.config.JwtTokenProvider;
import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.Member;

import com.example.demo.member.service.EmailService;
import com.example.demo.member.service.MemberService;
import com.example.demo.profile.domain.userinfo.UserInfo;
import com.example.demo.profile.domain.userinfo.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class MemberController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final UserInfoRepository userInfoRepository;


    @PostMapping("/member/save")
    public ResponseEntity<Boolean> save(@RequestBody MemberDTO memberDTO) {
        boolean save_check;

        ///창민님 코드
        // 회원가입할때

        UserInfo emptyUserInfo = UserInfo.builder().build();
        userInfoRepository.save(emptyUserInfo);

        memberService.save(memberDTO, emptyUserInfo);
        save_check = true;

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save_check);

    }

    @PostMapping("/member/login")
    public ResponseEntity<String> login(@RequestBody  Map<String, String> member) {
        System.out.println("여기는 /member/login안!");
        System.out.println(member);


        String loginResult = memberService.login(member);

        if (loginResult != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResult);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            //return null;

        }

    }

    @PostMapping("member2/logincheck")
    public String login_after_checkpring(Authentication authentication) {

        // 로그인한 아이디!
        String loginResult_check = "나오나..?";
        String ty = authentication.getName();

        return ty;

    }




    //아래 만든게 회원가입할때 id 복수 체크 부분
    @GetMapping("/member/save/{user_id}")
    public  ResponseEntity<Boolean> checkIDExist(@PathVariable String user_id) {
        Optional<Member> memberEntityCheckIdExist,memberEntityCheckNicknameExist;
        memberEntityCheckIdExist = memberService.findOne_withID(user_id);
        memberEntityCheckNicknameExist = memberService.findOne_withnickname(user_id);
        System.out.println(memberEntityCheckIdExist);
        boolean check_id_exist;
        if(memberEntityCheckIdExist.isPresent() || memberEntityCheckNicknameExist.isPresent()) {
            // 이미 db에 존재한다 즉 아이디로 부적절!
            check_id_exist = true;
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(check_id_exist);
        }
        else {
            // db에 아이디가 없어 아이디로 사용가능!
            check_id_exist = false;
            //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(check_id_exist);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(check_id_exist);
        }



    }


    // 아이디찾기
    // 일단은 이름과 생년월일, 이메일으로 -> 아이디 찾기 진행함!
    @GetMapping("/member/idSearch")
    public String UserIdSearch(@RequestBody MemberDTO memberDTO){
        return memberService.Idfind(memberDTO);
    }

    @GetMapping("/email/{send_email}")
    public String temp(@RequestBody MemberDTO memberDTO,@PathVariable String send_email){

        String changepw = memberService.changepw(memberDTO);
        System.out.println(changepw);

        EmailService.send(send_email,changepw);
        return "성공";

    }





}






