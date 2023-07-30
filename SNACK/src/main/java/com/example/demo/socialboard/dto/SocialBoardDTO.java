package com.example.demo.socialboard.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

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

    private Long writerId;

    private String content;

    private Long likes = 0L;




}
