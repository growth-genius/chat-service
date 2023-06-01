package com.gg.tgather.chattingservice.modules.chat.repository.chatroom;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomSearchDto;
import java.util.List;

public interface ChatRoomRepositoryQuerydsl {

    List<ChatRoomSearchDto> findAllRoomByAccountId(String accountId);

    ChatRoomDto findRoomById(String roomId);

}
