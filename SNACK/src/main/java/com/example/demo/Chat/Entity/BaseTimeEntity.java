package com.example.demo.Chat.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass // BaseEntity를 상속한 엔티티들은 아래 필드들을 컬럼으로 인식하게 된다.
@EntityListeners(AuditingEntityListener.class)  // Auditing(자동으로 값 매핑) 기능 추가
public abstract class BaseTimeEntity {

    @CreatedDate	// Entitiy가 최초로 생성되어 저장될 때 시간이 자동으로 저장됩니다.
    private LocalDateTime createdTime;

    @LastModifiedDate	// 조회한 Entity의 값을 변경할 때 시간이 자동으로 저장됩니다.
    private LocalDateTime lastModifiedTime;
}