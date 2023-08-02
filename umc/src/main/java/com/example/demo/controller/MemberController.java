package com.example.demo.controller;

//import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.MemberEntity;
import com.example.demo.profile.domain.UserInfo;
import com.example.demo.profile.domain.UserInfoRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {


    // 생성자 주입
    @Autowired
    private final MemberService memberService;

    @Autowired
    private final UserInfoRepository userInfoRepository;

    //private final JwtTokenProvider jwtTokenProvider;



    /*
    //08/03 원본태연코드
    // 아래 PostMApping /member/save 는 데이터베이스에 넣기전일단 memberDTO 확인한느것!
    // 즉 아래는 save함수는 DB에 넣기 위해 사용!
    // 즉 아래는 회원가입!
    @PostMapping("/member/save")
    public boolean save(@RequestBody MemberDTO memberDTO) {
        boolean save_check;

        System.out.println("MemberController.save~!~!~!~!");
        System.out.println("제발.. : " + memberDTO);

        ///창민님 코드
        // 회원가입할때

        UserInfo emptyUserInfo = UserInfo.builder().build();
        userInfoRepository.save(emptyUserInfo);




        ///창민님 코드


        memberService.save(memberDTO);
        save_check = true;



        return save_check;
    }*/

    //08/03 창민님 코드!
    // 아래 PostMApping /member/save 는 데이터베이스에 넣기전일단 memberDTO 확인한느것!
    // 즉 아래는 save함수는 DB에 넣기 위해 사용!
    // 즉 아래는 회원가입!
    /*
    // 08.03 태연 원본
    @PostMapping("/member/save")
    public boolean save(@RequestBody MemberDTO memberDTO) {
        boolean save_check;

        System.out.println("MemberController.save~!~!~!~!");
        System.out.println("제발.. : " + memberDTO);

        ///창민님 코드
        // 회원가입할때

        UserInfo emptyUserInfo = UserInfo.builder().build();
        userInfoRepository.save(emptyUserInfo);

        memberService.save(memberDTO, emptyUserInfo);
        save_check = true;

        return save_check;
    }*/

    // 08.03 태연-정화
    @PostMapping("/member/save")
    public ResponseEntity<Boolean> save(@RequestBody MemberDTO memberDTO) {
        boolean save_check;

        System.out.println("MemberController.save~!~!~!~!");
        System.out.println("제발.. : " + memberDTO);

        ///창민님 코드
        // 회원가입할때

        UserInfo emptyUserInfo = UserInfo.builder().build();
        userInfoRepository.save(emptyUserInfo);

        memberService.save(memberDTO, emptyUserInfo);
        save_check = true;

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save_check);

    }



    //login할때 사용!
    /*@PostMapping("/member/login")
    public ResponseEntity<MemberDTO> login(@RequestBody MemberDTO memberDTO) {
        System.out.println("여기는 /member/login안!");
        System.out.println(memberDTO);


        MemberDTO loginResult = memberService.login(memberDTO);

        if (loginResult != null) {
            //login 성공!!
            //response entity
            //
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResult);
        } else {
            // 실패!
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

    }*/

    ////27일 도전 - 성공!!

    /*@PostMapping("/member/login")
    public String login(@RequestBody  Map<String, String> member) {
        System.out.println("여기는 /member/login안!");
        System.out.println(member);


        String loginResult = memberService.login(member);

        if (loginResult != null) {
            //login 성공!!
            //response entity
            System.out.println("여기는 loginResult 안!!");
            System.out.println(loginResult);
            return null;
            //return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResult);
        } else {
            // 실패!
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return null;

        }

    }*/

    @PostMapping("/member/login")
    public ResponseEntity<String> login(@RequestBody  Map<String, String> member) {
        System.out.println("여기는 /member/login안!");
        System.out.println(member);


        String loginResult = memberService.login(member);

        if (loginResult != null) {
            //login 성공!!
            //response entity
            System.out.println("여기는 loginResult 안!!");
            System.out.println(loginResult);
            //return null;
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResult);
        } else {
            // 실패!
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            //return null;

        }

    }

    //
    @PostMapping("/member/login_after/{user_token}")
    public String login_after_find_getUserPk(@PathVariable String user_token) {



        String loginResult = memberService.login_find_getUserPk(user_token);

        return loginResult;

    }





    //아래 만든게 회원가입할때 id 복수 체크 부분
    @PostMapping("/member/save/{user_id}")
    public  ResponseEntity<Boolean> checkIDExist(@PathVariable String user_id) {

        Optional<MemberEntity> memberEntity_cehckIDExist,memberEntity_checknicknameExist;
        memberEntity_cehckIDExist = memberService.findOne_withID(user_id);
        memberEntity_checknicknameExist = memberService.findOne_withnickname(user_id);
        System.out.println(memberEntity_cehckIDExist);
        boolean check_id_exist;
        if(memberEntity_cehckIDExist.isPresent() || memberEntity_checknicknameExist.isPresent()) {
            // 이미 db에 존재한다 즉 아이디로 부적절!
            check_id_exist = true;
        }
        else {
            // db에 아이디가 없어 아이디로 사용가능!
            check_id_exist = false;
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(check_id_exist);


    }


    // 아이디찾기
    // 일단은 이름과 생년월일, 이메일으로 -> 아이디 찾기 진행함!
    @GetMapping("/member/idSearch")
    public String UserIdSearch(@RequestBody MemberDTO memberDTO){
        /*System.out.println("여기는 idSearch안!");
        System.out.println(memberDTO);
        String return_id = memberService.Idfind(memberDTO);*/

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






