package com.example.demo.socialboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.MemberEntity;
import com.example.demo.socialboard.entity.SocialBoard;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    private MemberEntity writer;

    private String content;

    private Long likes = 0L;

    private List<CommentDTO> comments = new ArrayList<>();

    public abstract SocialBoard toEntity();

}
