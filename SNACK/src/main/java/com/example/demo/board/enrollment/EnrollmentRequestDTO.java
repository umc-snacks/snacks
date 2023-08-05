package com.example.demo.board.enrollment;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentRequestDTO {
    @NotNull
    private Long memberId;

    @NotNull
    private Long boardId;

}
