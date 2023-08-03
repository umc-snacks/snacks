package com.example.demo.board;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.board.dto.BoardRequestDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.entity.BoardSearch;
import com.example.demo.exception.BoardSizeOverException;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.repository.BoardSearchRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardSearchRepositoryImpl boardSearchRepositoryImpl;
    private final MemberRepository memberRepository;

    private final BoardMemberRepository boardMemberRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, BoardSearchRepositoryImpl boardSearchRepositoryImpl, MemberRepository memberRepository, BoardMemberRepository boardMemberRepository) {
        this.boardRepository = boardRepository;
        this.boardSearchRepositoryImpl = boardSearchRepositoryImpl;
        this.memberRepository = memberRepository;
        this.boardMemberRepository = boardMemberRepository;
    }

    // 생성 로직

    public Long saveBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public Board buildBoard(BoardRequestDTO boardRequestDTO) throws BoardSizeOverException {
        List<BoardMember> boardMembers = new ArrayList<>();
        List<Long> memberIds = boardRequestDTO.getMemberIds();

        boardSizeCheck(boardRequestDTO, memberIds);

        for (Long memberId : memberIds) {
            BoardMember boardMember = new BoardMember();
            boardMember.setMember(memberRepository.findById(memberId)
                    .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + memberId)));
            boardMembers.add(boardMember);
        }

        Long writerId = boardRequestDTO.getWriterId();
        Member writer = memberRepository.findById(writerId)
                .orElseThrow(() -> new NoSuchElementException("Could not found writer id : " + writerId));

        Board board = BoardRequestDTO.toEntity(boardRequestDTO, boardMembers, writer);
        boardMemberRepository.saveAll(boardMembers);
        boardRepository.save(board);

        return board;
    }

    public Optional<Board> getBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }


    public void updateBoard(Long boardId, BoardRequestDTO requestDTO) {
        // writerId, memberIds는 못바꿈
        Board updatedBoard = BoardRequestDTO.toEntity(requestDTO, new ArrayList<>(), null);
        Board existingBoard = boardRepository.findById(boardId)
                        .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + boardId));

        update(updatedBoard, existingBoard);

        boardRepository.save(existingBoard);
    }

    private static void update(Board updatedBoard, Board existingBoard) {
        existingBoard.setTitle(updatedBoard.getTitle());
        existingBoard.setGameTitle(updatedBoard.getGameTitle());
        existingBoard.setEtcTitle(updatedBoard.getEtcTitle());

        existingBoard.setDate(updatedBoard.getDate());

        existingBoard.setNotice(updatedBoard.getNotice());
        existingBoard.setMaxCount(updatedBoard.getMaxCount());

        existingBoard.setAutoCheckIn(updatedBoard.isAutoCheckIn());
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }


    public List<Board> searchBoard(BoardSearch boardSearch) {
        return boardSearchRepositoryImpl.searchBoard(boardSearch);
    }





    private static void boardSizeCheck(BoardRequestDTO boardRequestDTO, List<Long> memberIds) throws BoardSizeOverException {
        int maxMember = boardRequestDTO.getMaxCount();
        if ( memberIds.size() > maxMember){
            throw new BoardSizeOverException("멤버수가 설정한것보다 많습니다");
        }
    }

}
