package com.example.demo.member.repository;


import com.example.demo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {


    // 회원아이디로 회원 정보 조회 (select * from 테이블명 where id =?)
    // 이 이름을 내마음대로 지을수가 없네..?
    Optional<Member> findByLoginId(String id);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByName(String name);

    Optional<Member> findByNameAndLoginIdAndBirth(String name, String id, LocalDate date);




}
