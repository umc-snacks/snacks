package com.example.demo.socialboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.MemberEntity;
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

    private MemberEntity writer;

    private String content;

    private Long likes = 0L;

    public abstract SocialBoard toEntity(Member writer, List<Comment> comments);


}
