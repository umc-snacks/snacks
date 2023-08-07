package com.example.demo.board;

import com.example.demo.Chat.ChatService;
import com.example.demo.board.dto.BoardRequestDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.entity.BoardSearch;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.repository.BoardSearchRepositoryImpl;
import com.example.demo.entity.Member;
import com.example.demo.exception.BoardSizeOverException;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardSearchRepositoryImpl boardSearchRepositoryImpl;
    private final MemberRepository memberRepository;

    private final BoardMemberRepository boardMemberRepository;
    private final ChatService chatService;

    @Autowired
    public BoardService(BoardRepository boardRepository, BoardSearchRepositoryImpl boardSearchRepositoryImpl, MemberRepository memberRepository, BoardMemberRepository boardMemberRepository, ChatService chatService) {
        this.boardRepository = boardRepository;
        this.boardSearchRepositoryImpl = boardSearchRepositoryImpl;
        this.memberRepository = memberRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.chatService = chatService;
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
        Board boardWithChatRoomAdded = chatService.createBoardChatRoom(board, boardMembers);
        boardRepository.save(boardWithChatRoomAdded);

        // board 생성시 채팅방도 함께 생성

        return board;
    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 id의 게시판이 존재하지 않습니다."));
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
