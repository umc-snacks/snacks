package com.example.demo.repository;


import com.example.demo.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {


    // 회원아이디로 회원 정보 조회 (select * from 테이블명 where id =?)
    // 이 이름을 내마음대로 지을수가 없네..?
    Optional<MemberEntity> findById(String id);

    Optional<MemberEntity> findByNickname(String nickname);

    Optional<MemberEntity> findByName(String name);

    Optional<MemberEntity> findByNameAndIdAndBirth(String name,String id, LocalDate date);




}
