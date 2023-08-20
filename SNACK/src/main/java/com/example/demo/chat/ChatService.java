package com.example.demo.chat;

import com.example.demo.chat.Dto.*;
import com.example.demo.chat.Dto.ChatRoomDTO.Get;
import com.example.demo.chat.Entity.ChatMessage;
import com.example.demo.chat.Entity.ChatRoom;
import com.example.demo.chat.Entity.ChatRoomMember;
import com.example.demo.chat.Entity.ChatRoomMemberId;
import com.example.demo.chat.repository.ChatMessageRepository;
import com.example.demo.chat.repository.ChatRoomMemberRepository;
import com.example.demo.chat.repository.ChatRoomRepository;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardMember;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.RestApiException;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class ChatService {
    private final MemberRepository memberRepository; // 나중에 멤버 서비스의 메소드로 대체할 예정
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    /*
     * 사용자가 속한 채팅방과 팔로워를 모두 조회하여 리턴한다.
     *
     * @param Long memberId 사용자 식별자
     * @return 사용자가 속한 채팅방 리스트를 DTO로 반환
     */
    @Transactional(readOnly = true)
    public ChatRoomListDTO getChatList(Long memberId) {
        log.info("[Execute ChatService Method] getChatList");

        List<Object[]> crmAndrt = chatRoomMemberRepository.findMembersWithSameRoomAndReceiveTime(memberId);

        // 모든 가입자 정보를 가져옴
        List<Member> memberList = memberRepository.findAll();

        List<MemberSearchDTO> mdtoList = memberList.stream()
                .filter(m -> !m.getId().equals(memberId))// !! 본인 제외하고 retrun 확인 X
                .map(Mapper::MemberToMemberSearchDTO)
                .collect(Collectors.toList());


        Map<Long, ChatRoomDTO.Get> chatMap = new HashMap<>();

        for (Object[] list : crmAndrt) {
            ChatRoomMember crm = (ChatRoomMember) list[0];
            LocalDateTime time = (LocalDateTime) list[1];
            String content = (String) list[2];
            Board crmBoard = crm.getChatRoom().getBoard();
            Member crmMember = crm.getMember();

            Long roomId = crm.getChatRoom( ).getRoomId();
            ChatRoomDTO.Get cr = chatMap.get(roomId);

            if (cr == null && time != null) {
                cr = new ChatRoomDTO.Get();
                boolean isBoard = crmBoard != null;

                cr.setType(isBoard ? ChatType.BOARD.name() : ChatType.PRIVATE.name());
                cr.setRoomId(roomId);
                cr.setMemberId(isBoard ? roomId : crmMember.getId());
                cr.setName(isBoard ? crmBoard.getTitle() : crmMember.getNickname());
                cr.setNumberOfUnreadMessage(chatMessageRepository.getNumberOfUnreadMessage(memberId, roomId));
                cr.setContent(content);
                cr.setSentAt(time);
                cr.setAppointment(isBoard ? crmBoard.getDate() : null);
                cr.setImageUri(isBoard ? "boardImageUri" : "memberImageUri");	// 하드코딩
//				cr.setImageUri(isBoard ? crmBoard.geturi : crmMember.getProfileimageurl());

                chatMap.put(roomId, cr);
            }
        }

        List<Get> chatRoomList = chatMap.values().stream()
                .sorted(Comparator.comparing(ChatRoomDTO.Get::getSentAt).reversed())
                .collect(Collectors.toList());

        return ChatRoomListDTO.builder()
                .ChatRoomDTOGetList(chatRoomList)
                .memberList(mdtoList)
                .build();
    }

    /*
     * board에 어떤 사용자가 추가되었을 때 채팅방에 해당 사용자를 추가하는 메서드
     */
    public Board createBoardChatRoom(Board board, List<BoardMember> members) {
        log.info("[Execute ChatService Method] createBoardChatRoom");
        ChatRoom chatRoom = chatRoomRepository.saveAndFlush(new ChatRoom());
        board.setChatRoom(chatRoom);

        chatRoom.setBoard(board);
        chatRoomRepository.save(chatRoom);

        for (BoardMember bm : members) {
            ChatRoomMember chatRoomMember = createChatRoomMember(chatRoom, bm.getMember());
            chatRoomMemberRepository.save(chatRoomMember);
        }

        return board;
    }

    /*
     * board에 추가 인원이 들어왔을 때 해당 인원을 board의 채팅방에 포함시키는 메소드
     */
    public void addMemberToChatRoom(BoardMember boardMember) {
        log.info("[Execute ChatService Method] addMemberToChatRoom");
        ChatRoom chatRoom = boardMember.getBoard().getChatRoom();
        Member member = boardMember.getMember();

        ChatRoomMember chatRoomMember = createChatRoomMember(chatRoom, member);
        chatRoomMemberRepository.save(chatRoomMember);
    }

    /*
     * board에서 나가는 인원이 있을 때 해당 인원을 board의 채팅방에서 내보내는 메소드
     */
    public void deleteMemberToChatRoomd(BoardMember boardMember) {
        log.info("[Execute ChatService Method] createPrivateChatRoom");
        ChatRoom chatRoom = boardMember.getBoard().getChatRoom();
        Member member = boardMember.getMember();

        chatRoomMemberRepository.deleteMemberToChatRoom(member.getId(), chatRoom.getRoomId());
    }

    /*
     * ChatRoomMember을 만들 수요가 많아서 만든 메서드
     */
    private ChatRoomMember createChatRoomMember(ChatRoom chatRoom, Member member) {
        ChatRoomMemberId crmId = new ChatRoomMemberId(
                chatRoom.getRoomId(),
                member.getId()
        );

        return ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .chatRoomMemberId(crmId)
                .member(member)
                .readTime(LocalDateTime.now())
                .build();
    }


    /*
     * 채팅방을 생성하는 메서드, A가 B에게 처음 채팅을 걸려고 할 때
     *
     * @param userName B의 닉네임에 해당
     * @return chatRoom.getRoomId()
     */
    @Transactional
    public Long createPrivateChatRoom(Long creatorId, Long participantId) {
        log.info("[Execute ChatService Method] createPrivateChatRoom");

        if (creatorId.equals(participantId)) throw new RestApiException(ErrorCode.BAR_REQUEST);

        /*
         * 어떤 오류로 방이 있는데 또 방이 생성될 경우 로직 처리 필요
         */

        Member creator = verifiedMember(creatorId);
        Member participant = verifiedMember(participantId);

        ChatRoom chatRoom = chatRoomRepository.saveAndFlush(new ChatRoom());

        ChatRoomMember chatRoomMemeberCreater = createChatRoomMember(chatRoom, creator);
        ChatRoomMember chatRoomMemeberParticipant = createChatRoomMember(chatRoom, participant);

        chatRoomMemberRepository.save(chatRoomMemeberCreater);
        chatRoomMemberRepository.save(chatRoomMemeberParticipant);

        return chatRoom.getRoomId();
    }

    /*
     * 사용자가 전송한 메시지를 db에 저장함
     *
     * @param MessageDTO.Request mdto 메시지 틀
     * @return 없음 exception
     */
    @Transactional
    public void saveMessage(MessageDTO.Request mdto) {
        log.info("[Execute ChatService Method] saveMessage");

        ChatRoom ChatRoomEntity = verifiedChatRoom(mdto.getRoomId());
        Member memberEntity = verifiedMember(mdto.getSenderId());

        ChatMessage chatMessageEntity = ChatMessage.builder()
                .chatRoom(ChatRoomEntity)
                .sender(memberEntity)
                .content(mdto.getContent())
                .sentAt(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessageEntity);
    }

    /*
     * 사용자가 채팅방에 들어갔을 때 안 읽은 메시지들을 보내줌
     */
    @Transactional(readOnly = true)
    public List<MessageDTO.Response> getChatMessage(Long memberId, Long roomId) {
        log.info("[Execute ChatService Method] getChatMessage");

        List<ChatMessage> unreadChatMessages = chatMessageRepository.getUnreadMessages(memberId, roomId);
        List<MessageDTO.Response> messageDTOResponse = new ArrayList<>();

        for (ChatMessage cm : unreadChatMessages) {
            MessageDTO.Response mdtor = Mapper.ChatMessageToMessageDtoResponse(cm);
            messageDTOResponse.add(mdtor);
        }

        return messageDTOResponse;
    }

    /*
     * 채팅방 삭제
     */
    @Transactional
    public void deleteChatRoom(Long memberId, Long roomId) {
        log.info("[Execute ChatService Method] deleteChatRoom, params");
        chatMessageRepository.deleteByRoomId(roomId);
        chatRoomMemberRepository.deleteByRoomId(roomId);
        chatRoomRepository.deleteByRoomId(roomId);
    }

    /*
     * 매일 4시, 3일이 지난 모든 채팅 삭제
     */
    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    public void deleteChatMessage() {
        log.info("[Execute ChatService Method] deleteChatMessage");
        chatMessageRepository.deleteMessagePeriodicallyEvery3Day();
    }

    // 사용자가 어떤 채팅방을 읽은 시간을 저장함
    @Transactional
    public void setReadingTime(Long memberId, Long roomId) throws NullPointerException {
        log.info("[Execute ChatService Method] setReadingTime");

        try {
            ChatRoomMember crm = chatRoomMemberRepository.findByMemberIdAndRoomId(memberId, roomId);
            crm.setReadTime(LocalDateTime.now());
            chatRoomMemberRepository.save(crm);
        } catch (NullPointerException e) {
            throw new RestApiException(ErrorCode.MEMBER_OR_ROOM_NOT_FOUND);
        }
    }

    public Member verifiedMember(Long memberId) throws RestApiException {

        Optional<Member> optionalMemberEntity = memberRepository.findById(memberId);
        return optionalMemberEntity.orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public ChatRoom verifiedChatRoom(Long roomId) {

        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(roomId);
        return optionalChatRoom.orElseThrow(() -> new RestApiException(ErrorCode.ROOM_NOT_FOUND));
    }
}


