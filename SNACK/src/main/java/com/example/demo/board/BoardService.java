package com.example.demo.board;

import com.example.demo.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 생성 로직
    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    public Board createBoard(Member member, String title, String gameTitle, String notice) {
        Board board = new Board();
        board.setTitle(title);
        board.setGameTitle(gameTitle);
        board.setDate(LocalDate.now());

        ArrayList<Member> members = getMemberList(member);
        board.setMembers(members);

        board.setNotice(notice);
        board.setMemberCount(1);
        return board;
    }

    private static ArrayList<Member> getMemberList(Member member) {
        ArrayList<Member> members = new ArrayList<>();
        members.add(member);
        return members;
    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseGet(Board::new);

    }


}
