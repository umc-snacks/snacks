package com.example.demo.board.enrollment;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentRequestDTO {
    @Null
    private Long memberId;

    @NotNull
    private Long boardId;

}
