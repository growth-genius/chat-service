package com.gg.tgather.chattingservice.modules.chat.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomInfo {

    private String accountId;

    private List<ChatRoomDto> chatRoomList;

}
