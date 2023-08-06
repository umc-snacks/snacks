package com.example.demo.socialboard.dto;

import com.example.demo.Member.Member;
import com.example.demo.comment.entity.Comment;
import com.example.demo.socialboard.entity.SocialBoard;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    private Long writerId;

    private String content;

    private Long likes = 0L;

    public abstract SocialBoard toEntity(Member writer, List<Comment> comments);


}
