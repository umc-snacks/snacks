package com.example.demo.Chat.Entity;


import com.example.demo.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long roomId;
    
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id")
//    private Team team;
    
}