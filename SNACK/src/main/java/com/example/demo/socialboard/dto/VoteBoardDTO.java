package com.example.demo.socialboard.dto;


import com.example.demo.socialboard.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteBoardDTO extends SocialBoardDTO{

    private List<Vote> votes = new ArrayList<>();


}
