package com.gg.tgather.chattingservice.modules.chat.repository.chatmessage;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import java.util.List;

public interface ChatMessageJdbcRepository {
    void batchInsert(List<ChatMessageDto> chatMessageDtoList);

}
