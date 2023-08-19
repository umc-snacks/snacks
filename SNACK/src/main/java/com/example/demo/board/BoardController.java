package com.example.demo.board;

import com.example.demo.board.dto.BoardRequestDTO;
import com.example.demo.board.dto.BoardResponseDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.entity.BoardSearch;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.exception.BoardHostAuthenticationException;
import com.example.demo.exception.BoardSizeOverException;
import com.example.demo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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
    public ResponseEntity create(@RequestBody @Validated BoardRequestDTO boardRequestDTO, Authentication authentication, BindingResult bindingResult) throws BoardSizeOverException {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Board board = boardService.buildBoard(boardRequestDTO, authentication);

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
        List<Board> boards = boardService.getBoards();
        List<BoardResponseDTO> responseDTOList = boards.stream()
                .map(BoardResponseDTO::getBuild)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseDTOList);

    }

    @PutMapping("{boardId}")
    public ResponseEntity<?> update(@Valid @RequestBody BoardRequestDTO boardRequestDTO, @PathVariable Long boardId, Authentication authentication) throws BoardHostAuthenticationException {
        Board updateBoard = boardService.updateBoard(boardId, boardRequestDTO, authentication);
        BoardResponseDTO responseDTO = BoardResponseDTO.getBuild(updateBoard);

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("{boardId}")
    public void delete(@PathVariable Long boardId, Authentication authentication) throws BoardHostAuthenticationException {
        List<BoardMember> boardMembers = boardMemberRepository.findBoardMembersByBoardId(boardId);
        boardMembers.stream().iterator().forEachRemaining(
                boardMemberRepository::delete
        );

        boardService.deleteBoard(boardId, authentication);
    }

    @GetMapping("boards")
    public ResponseEntity findBoardList(@Validated BoardSearch boardSearch) {
        return ResponseEntity.ok().body(boardService.searchBoard(boardSearch));
    }
}