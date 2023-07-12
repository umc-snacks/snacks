package com.example.demo.board;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @Autowired
    public BoardController(BoardService boardService, MemberService memberService) {
        this.boardService = boardService;
        this.memberService = memberService;
    }

    @PostMapping
    public String create(@RequestBody Board board) {

        Member member = board.getMembers().get(0);
        boardService.saveBoard(board);

        member.setBoard(board);
        System.out.println(member.getName());
        memberService.saveMember(member);

        return "ok";
    }

}
