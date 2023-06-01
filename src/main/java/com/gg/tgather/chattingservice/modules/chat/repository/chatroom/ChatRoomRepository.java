package com.gg.tgather.chattingservice.modules.chat.repository.chatroom;

import com.gg.tgather.chattingservice.modules.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>, ChatRoomRepositoryQuerydsl {

}