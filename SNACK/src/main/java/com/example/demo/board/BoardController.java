package com.example.demo.board;

import com.example.demo.Member.MemberService;
import com.example.demo.board.dto.BoardRequestDTO;
import com.example.demo.board.dto.BoardResponseDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.entity.BoardSearch;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.exception.BoardSizeOverException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;


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
    public ResponseEntity create(@Valid @RequestBody BoardRequestDTO boardRequestDTO,
                                 UriComponentsBuilder uriBuilder) throws BoardSizeOverException {
        Board board = boardService.buildBoard(boardRequestDTO);

        List<BoardMember> boardMembers = board.getBoardMembers();

        for (int i = 0; i < boardMembers.size(); i++) {
            BoardMember boardMember = boardMembers.get(i);
            boardMember.setBoard(board);
            boardMemberRepository.save(boardMember);
        }

        BoardResponseDTO responseDTO = BoardResponseDTO.getBuild(board);
        URI uri = buildUri(uriBuilder, responseDTO);

        // 응답 헤더의 Location 에 생성된 리소스 주소 반환
        return ResponseEntity.created(uri).build();
    }

        private static URI buildUri(UriComponentsBuilder uriBuilder, BoardResponseDTO responseDTO) {
            URI uri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
                    .withMethodCall(on(BoardController.class).read(responseDTO.getId()))
                    .build().encode().toUri();
            return uri;
        }

    @GetMapping("{boardId}")
    public ResponseEntity<BoardResponseDTO> read(@PathVariable Long boardId) {
        Board board = boardService.getBoard(boardId).orElseThrow(() -> new NoSuchElementException("해당 id의 게시판이 존재하지 않습니다."));
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
