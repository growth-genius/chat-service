package com.gg.tgather.chattingservice.modules.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="room_id")
    private ChatRoom chatRoom;

    private String accountId;

    public static ChatAccount of(String accountId, ChatRoom chatRoom) {
        ChatAccount chatAccount = new ChatAccount();
        chatAccount.accountId = accountId;
        chatAccount.chatRoom = chatRoom;
        return chatAccount;
    }

}
