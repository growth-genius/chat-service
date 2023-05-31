package com.gg.chattingservice.modules.chat.dto;

import com.gg.chattingservice.modules.chat.entity.ChatAccount;
import com.gg.chattingservice.modules.chat.entity.ChatRoom;
import lombok.Data;

@Data
public class ChatRoomSearchDto {

    private String roomId;
    private String roomName;
    private String accountId;
    private int accountCount;

    public ChatRoomSearchDto(ChatRoom chatRoom, ChatAccount chatAccount, int accountCount) {
        this.roomId = chatRoom.getRoomId();
        this.roomName = chatRoom.getRoomName();
        this.accountId = chatAccount.getAccountId();
        this.accountCount = accountCount;
    }

}
