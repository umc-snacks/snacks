package com.example.demo.Member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDTO {
    private String name;

    private String nickname;

    private String email;
}
