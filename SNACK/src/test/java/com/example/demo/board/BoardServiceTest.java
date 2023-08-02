package com.example.demo.board;

import com.example.demo.Games;
import com.example.demo.Member.Member;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    void saveBoard() throws Exception {
        Board board = new Board();
        board.setTitle("아무나 오세요");
        board.setGameTitle(Games.valueOf("LeagueOfLegends"));

        Member member = new Member();
        member.setName("홍길동");

        Long boardId = boardService.saveBoard(board);

        Board boardById = boardRepository.findById(boardId).orElseThrow(Exception::new);

        Assertions.assertThat(board.getId()).isEqualTo(boardById.getId());
    }

    @Test
    void getBoard() {
    }

    @Test
    void updateBoard() {
        // when
        Board board = new Board();
        board.setTitle("아무나 오세요");


        Board updateBoard = new Board();
        updateBoard.setTitle("새 게임");

        // given
        Long boardId = boardService.saveBoard(board);
        boardService.updateBoard(boardId, updateBoard);

        // then
        Assertions.assertThat(boardId).isEqualTo(board.getId());
        Assertions.assertThat(board.getTitle()).isEqualTo("새 게임");
    }

    @Test
    void deleteBoard() {
    }
}