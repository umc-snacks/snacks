package com.example.demo.board;

import com.example.demo.board.dto.BoardRequestDTO;
import com.example.demo.board.dto.BoardResponseDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.entity.BoardSearch;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.exception.BoardSizeOverException;
import com.example.demo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/board/")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final BoardMemberRepository boardMemberRepository;

    @Autowired
    public BoardController(BoardService boardService, MemberService memberService, BoardMemberRepository boardMemberRepository) {
        this.boardService = boardService;
        this.memberService = memberService;
        this.boardMemberRepository = boardMemberRepository;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody BoardRequestDTO boardRequestDTO) throws BoardSizeOverException {
        Board board = boardService.buildBoard(boardRequestDTO);

        List<BoardMember> boardMembers = board.getBoardMembers();

        for (int i = 0; i < boardMembers.size(); i++) {
            BoardMember boardMember = boardMembers.get(i);
            boardMember.setBoard(board);
            boardMemberRepository.save(boardMember);
        }

        BoardResponseDTO responseDTO = BoardResponseDTO.getBuild(board);

        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("{boardId}")
    public ResponseEntity<BoardResponseDTO> read(@PathVariable Long boardId) {
        Board board = boardService.getBoard(boardId);
        BoardResponseDTO responseDTO = BoardResponseDTO.getBuild(board);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<?> readAll() {
        return ResponseEntity.ok().body(boardService.getBoards());
    }

    @PutMapping("{boardId}")
    public void update(@Valid @RequestBody BoardRequestDTO boardRequestDTO, @PathVariable Long boardId) {
        boardService.updateBoard(boardId, boardRequestDTO);
    }

    @DeleteMapping("{boardId}")
    public void delete(@PathVariable Long boardId) {
        List<BoardMember> boardMembers = boardMemberRepository.findBoardMembersByBoardId(boardId);
        boardMembers.stream().iterator().forEachRemaining(
                boardMemberRepository::delete
        );


        boardService.deleteBoard(boardId);
    }

    @GetMapping("boards")
    public ResponseEntity findBoardList(@Validated BoardSearch boardSearch) {
        return ResponseEntity.ok().body(boardService.searchBoard(boardSearch));
    }
}