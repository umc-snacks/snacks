package com.example.demo.heart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeartRequestDTO {

    @NotNull(message = "멤버의 아이디를 입력해주세요")
    private Long memberId;

    @NotNull(message = "게시글의 아이디를 입력해주세요")
    private Long boardId;

}