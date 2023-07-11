package com.example.demo.board;


import com.example.demo.Member.MemberDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board/")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public String create(@RequestBody String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> dataMap = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});
            dataMap.get()

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @PostMapping("/11")
    public String test() {
        return "ok";
    }
}
