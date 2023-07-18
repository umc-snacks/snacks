package com.example.demo.board;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/board/")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @Autowired
    public BoardController(BoardService boardService, MemberService memberService) {
        this.boardService = boardService;
        this.memberService = memberService;
    }

    @PostMapping
    public String create(@Valid @RequestBody BoardDTO boardDTO) {
        Board board = Board.createBoard(boardDTO).orElseGet(Board::new);
        // board에서 게임 종류를 검색
        board.getMembers().iterator().forEachRemaining(
                member -> {
                    member.setBoard(board);
                    memberService.saveMember(member);
                }
        );

        return "ok";
    }

    @GetMapping("{boardId}")
    public Board read(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    @GetMapping
    public List<Board> readAll() {
        return boardService.getBoards();
    }

    @PutMapping("{boardId}")
    public void update(@Valid @RequestBody Board board, @PathVariable Long boardId) {
        boardService.updateBoard(boardId, board);
    }

    @DeleteMapping("{boardId}")
    public void delete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
