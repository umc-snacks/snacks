package com.example.demo.config;


import com.example.demo.member.service.MemberService;
import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity //
@RequiredArgsConstructor
@AllArgsConstructor
public class SpringSecurityConfig{

    private MemberService memberService;

    @Value("${jwt.secret}")
    String secretKey;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001"));

        config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
        config.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.httpBasic(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors
                        .configurationSource(corsConfigurationSource()))


                .authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()

                .requestMatchers("/status","/member/login","/email","/email/**", "/","/member/","/member/**","/member/save","/member/save/**","/swagger-ui/**").permitAll()

                        .requestMatchers(HttpMethod.POST,"*/**").authenticated()	// 어떠한 요청이라도 인증필요
                        .requestMatchers(HttpMethod.GET,"*/**").authenticated()	// 어떠한 요청이라도 인증필요
                        .requestMatchers(HttpMethod.DELETE,"*/**").authenticated()	// 어떠한 요청이라도 인증필요
                        .requestMatchers(HttpMethod.PUT,"*/**").authenticated()	// 어떠한 요청이라도 인증필요


        )


                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

        .logout(Customizer.withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(memberService, secretKey);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }






}
