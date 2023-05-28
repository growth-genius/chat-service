package com.gg.chattingservice.modules.chat.repository.chatmessage;

import com.gg.chattingservice.modules.chat.dto.ChatMessageDto;
import java.util.List;

public interface ChatMessageJdbcRepository {
    void batchInsert(List<ChatMessageDto> chatMessageDtoList);

}
