package com.example.demo.socialboard;

import com.example.demo.socialboard.dto.SocialBoardDTO;
import com.example.demo.socialboard.dto.SocialBoardResponseDTO;
import com.example.demo.socialboard.dto.VoteBoardResponseDTO;
import com.example.demo.socialboard.entity.SocialBoard;
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
@RequestMapping("/api/social-board/")
@Slf4j

public class SocialBoardController {
    private final SocialBoardService socialBoardService;

    @Autowired
    public SocialBoardController(SocialBoardService socialBoardService) {
        this.socialBoardService = socialBoardService;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody SocialBoardDTO boardDTO,
                                 UriComponentsBuilder uriBuilder) {

        SocialBoard board = socialBoardService.saveBoard(boardDTO);

        URI uri = buildUri(uriBuilder, board);

        return ResponseEntity.created(uri).build();
    }

    private static URI buildUri(UriComponentsBuilder uriBuilder, SocialBoard socialBoard) {
        URI uri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
                .withMethodCall(on(SocialBoardController.class).read(socialBoard.getId()))
                .build().encode().toUri();
        return uri;
    }

    @GetMapping("{boardId}")
    public ResponseEntity<SocialBoardResponseDTO> read(@PathVariable Long boardId) {
        SocialBoard board = socialBoardService.getBoard(boardId).orElseThrow(() -> new NoSuchElementException("해당 id의 게시판이 존재하지 않습니다."));

        SocialBoardResponseDTO socialBoardResponseDTO = board.toResponseEntity();
        return ResponseEntity.ok().body(socialBoardResponseDTO);
    }

    /*
    업데이트 요청 추가해야함
     */

    @DeleteMapping("{boardId}")
    public void delete(@PathVariable Long boardId) {
        socialBoardService.deleteBoard(boardId);
    }

}
