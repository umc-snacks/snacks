package com.example.demo.config;


import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity //
public class SpringSecurityConfig{






    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf().disable().cors().disable()

        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/status","/email","/email/**", "/","","/member/","/member/**","/member/save","/member/save/**","/swagger-ui/**").permitAll()
                .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요

        )

        .formLogin(login -> login	// form 방식 로그인 사용
                .loginPage("/member/login")

                //폼테그에서 action으로 정의하면 이걸로 적으면 되는걸로암!
                .loginProcessingUrl("/login-process22")
                //.loginProcessingUrl("/member/login/**")
                .usernameParameter("id")
                .passwordParameter("pw")
                //.defaultSuccessUrl("/member/**", true)	// 성공 시 dashboard로
                .permitAll()	// 대시보드 이동이 막히면 안되므로 얘는 허용

        )

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

        .logout(Customizer.withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)


        /*http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/status","/email","/email/**", "/","","/member/","/member/**","/member/save","/member/save/**","/swagger-ui/**").permitAll()
                .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요

        )

        .formLogin(login -> login	// form 방식 로그인 사용
                .loginPage("/member/login")

                //폼테그에서 action으로 정의하면 이걸로 적으면 되는걸로암!
                .loginProcessingUrl("/login-process22")
                //.loginProcessingUrl("/member/login/**")
                .usernameParameter("id")
                .passwordParameter("pw")
                .defaultSuccessUrl("/member/**", true)	// 성공 시 dashboard로
                .permitAll()	// 대시보드 이동이 막히면 안되므로 얘는 허용

        )

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

        .logout(Customizer.withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)*/



        return http.build();
    }



}
