package com.gg.tgather.chattingservice.modules.chat.repository.chatroom;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomSearchDto;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryQuerydsl {

    List<ChatRoomSearchDto> findAllRoomByAccountId(String accountId);

    Optional<ChatRoomDto> findRoomById(String roomId);

}
