package com.gg.chattingservice.modules.chat.repository.chatroom;

import com.gg.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.commonservice.jpa.Querydsl5Support;

public class ChatRoomRepositoryImpl extends Querydsl5Support implements ChatRoomRepositoryQuerydsl {

    protected ChatRoomRepositoryImpl() {
        super(ChatRoom.class);
    }


}
