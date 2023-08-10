package com.example.demo.config;

import com.example.demo.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {





    private final MemberService memberService;
    private final String SecretKey;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {




       final String authroization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authroization log :{}",authroization);

        // token 안보내면 block
        if(authroization == null || !authroization.startsWith("Bearer ")){

            log.error("authroization is null or not Bearer ");
            filterChain.doFilter(request,response);
            return ;
        }

        //Token 꺼내기
        String token = authroization.split(" ")[1];

        if(JwtTokenProvider.isExpired(token,SecretKey)){
            filterChain.doFilter(request,response);
            return ;
        }
        //UserName Toke 꺼내기!!

        String username=JwtTokenProvider.getUserName(token,SecretKey);
        log.info("username : ",username);
        //String username ="";
       //권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, List.of(new SimpleGrantedAuthority("user")));
        //Detail을 넣어줍니다.
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }


}