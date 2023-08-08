package com.example.demo.socialboard.dto;

import java.util.List;

import com.example.demo.comment.entity.Comment;
import com.example.demo.member.entity.Member;
import com.example.demo.socialboard.entity.SocialBoard;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "dtype",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "V", value = VoteBoardDTO.class)
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class SocialBoardDTO {

    private Member writer;

    private String content;

    private Long likes = 0L;

    public abstract SocialBoard toEntity(Member writer, List<Comment> comments);


}
