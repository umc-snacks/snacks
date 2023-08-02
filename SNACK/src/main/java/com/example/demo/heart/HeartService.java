package com.example.demo.heart;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.exception.HeartRequestException;
import com.example.demo.socialboard.repository.SocialBoardRepository;
import com.example.demo.socialboard.entity.SocialBoard;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final SocialBoardRepository socialBoardRepository;

    @Autowired
    public HeartService(HeartRepository heartRepository, MemberRepository memberRepository, SocialBoardRepository socialBoardRepository) {
        this.heartRepository = heartRepository;
        this.memberRepository = memberRepository;
        this.socialBoardRepository = socialBoardRepository;
    }



    @Transactional
    public void insert(HeartRequestDTO heartRequestDTO) throws Exception {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + heartRequestDTO.getMemberId()));

        SocialBoard board = socialBoardRepository.findById(heartRequestDTO.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + heartRequestDTO.getBoardId()));


        // 이미 좋아요되어있으면 에러 반환
        if (heartRepository.findByMemberAndSocialBoard(member, board).isPresent()){
            delete(heartRequestDTO);
            return;
        }

        Heart heart = Heart.builder()
                .socialBoard(board)
                .member(member)
                .build();

        socialBoardRepository.updateCount(board, true);
        heartRepository.save(heart);
    }

    @Transactional
    public void delete(HeartRequestDTO heartRequestDTO) {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not found member id : " + heartRequestDTO.getMemberId()));

        SocialBoard board = socialBoardRepository.findById(heartRequestDTO.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("Could not found board id : " + heartRequestDTO.getBoardId()));

        Heart heart = heartRepository.findByMemberAndSocialBoard(member, board)
                .orElseThrow(() -> new NoSuchElementException("Could not found heart id"));

        socialBoardRepository.updateCount(board, false);
        heartRepository.delete(heart);
    }

}
