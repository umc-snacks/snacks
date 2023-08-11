package com.example.demo.config;


import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

//@Component

//@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberService memberService;



    /*@Override
    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
        Optional<MemberEntity> findOne = memberService.findOne_withID(insertedUserId);
        MemberEntity member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));

        return User.builder()
                .username(member.getId())
                .password(member.getPw())
                .roles("user")
                .build()
                ;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // user = new Securityuser();// 요기에 db에 갔다온 결과가 필요 합니다. 유저클래스는 DTO객체 입니다.
        Member member = memberService.findMemberByLoginId(username);
        UserDetails build = null;

        try {
            User.UserBuilder userBuilder = User.withUsername(member.getLoginId()).password(member.getPw());
            userBuilder.authorities("user");
            build = userBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return build;
    }


}
