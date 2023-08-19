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



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

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
