package com.example.demo.Chat;

import java.time.LocalDateTime;

import com.example.demo.Member.MemberEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "member_id")
    private MemberEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipient", referencedColumnName = "member_id")
    private MemberEntity recipient;

    private String content;
    
    private Boolean isRead;
    
    private LocalDateTime sent_at;
    
    @Builder
    public ChatMessage() {}
}