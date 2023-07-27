package com.example.demo.socialboard.dto;

import com.example.demo.Member.Member;
import com.example.demo.socialboard.entity.SocialBoard;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.util.ArrayList;
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
public abstract class SocialBoardDTO {

    private Member writer;

    private String content;

    private Long likes;

    private List<CommentDTO> comments = new ArrayList<>();

    public abstract SocialBoard toEntity();

}
