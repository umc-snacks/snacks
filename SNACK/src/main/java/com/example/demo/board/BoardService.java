package com.example.demo.board;

import com.example.demo.Chat.ChatService;
import com.example.demo.board.dto.BoardRequestDTO;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.board.entity.BoardSearch;
import com.example.demo.board.repository.BoardMemberRepository;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.repository.BoardSearchRepositoryImpl;
import com.example.demo.exception.BoardHostAuthenticationException;
import com.example.demo.member.entity.Member;
import com.example.demo.exception.BoardSizeOverException;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
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

    public Board buildBoard(BoardRequestDTO boardRequestDTO, Authentication authentication) throws BoardSizeOverException {
        Long writerId = Long.parseLong(authentication.getName());

        // 멤버 리스트에 본인 넣기
        List<BoardMember> boardMembers = new ArrayList<>();
        List<Long> memberIds = boardRequestDTO.getMemberIds();
        memberIds.add(writerId);

        // 팀 생성보드 사이즈 확인
        boardSizeCheck(boardRequestDTO, memberIds);

        for (Long memberId : memberIds) {
            BoardMember boardMember = new BoardMember();
            boardMember.setMember(memberRepository.findById(memberId)
                    .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + memberId)));
            boardMembers.add(boardMember);
        }

        Member writer = memberRepository.findById(writerId)
                .orElseThrow(() -> new NoSuchElementException("Could not found writer id : " + writerId));

        Board board = BoardRequestDTO.toEntity(boardRequestDTO, boardMembers, writer);

        boardMemberRepository.saveAll(boardMembers);

        // board 생성시 채팅방도 함께 생성
        Board boardWithChatRoomAdded = chatService.createBoardChatRoom(board, boardMembers);
        boardRepository.save(boardWithChatRoomAdded);


        return board;
    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("해당 id의 게시판이 존재하지 않습니다."));
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }


    public Board updateBoard(Long boardId, BoardRequestDTO requestDTO, Authentication authentication) throws BoardHostAuthenticationException {
        // writerId, memberIds는 못바꿈
        Board existingBoard = boardRepository.findById(boardId)
                        .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + boardId));
        boardOwnerCheck(existingBoard, authentication);

        Board updatedBoard = BoardRequestDTO.toEntity(requestDTO, new ArrayList<>(), null);

        update(updatedBoard, existingBoard);

        boardRepository.save(existingBoard);

        return existingBoard;
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

    public void deleteBoard(Long boardId, Authentication authentication) throws BoardHostAuthenticationException {
        Board board = getBoard(boardId);
        boardOwnerCheck(board, authentication);

        boardRepository.delete(board);
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

    public void boardOwnerCheck(Board board, Authentication authentication) throws BoardHostAuthenticationException {
        Long currentUserId = Long.parseLong(authentication.getName());

        if ( board.getId() != currentUserId ){
            throw new BoardHostAuthenticationException("게시판의 주인이 아닙니다");
        }
    }

}
