package com.example.demo.board;

import com.example.demo.Member.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;


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
    public ResponseEntity create(@Valid @RequestBody BoardDTO boardDTO,
                                 UriComponentsBuilder uriBuilder) {
        Board board = Board.createBoard(boardDTO).orElseThrow(() -> new IllegalArgumentException("멤버수 초과!"));

        board.getMembers().iterator().forEachRemaining(
                member -> {
                    member.setBoard(board);
                    memberService.saveMember(member);
                }
        );

        URI uri = buildUri(uriBuilder, board);

        // 응답 헤더의 Location 에 생성된 리소스 주소 반환
        return ResponseEntity.created(uri).build();
    }

    private static URI buildUri(UriComponentsBuilder uriBuilder, Board board) {
        URI uri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
                .withMethodCall(on(BoardController.class).read(board.getId()))
                .build().encode().toUri();
        return uri;
    }

    @GetMapping("{boardId}")
    public ResponseEntity<Board> read(@PathVariable Long boardId) {
        Board board = boardService.getBoard(boardId).orElseThrow(() -> new NoSuchElementException("해당 id의 게시판이 존재하지 않습니다."));
        return ResponseEntity.ok().body(board);
    }

    @GetMapping
    public ResponseEntity<?> readAll() {
        return ResponseEntity.ok().body(boardService.getBoards());
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
