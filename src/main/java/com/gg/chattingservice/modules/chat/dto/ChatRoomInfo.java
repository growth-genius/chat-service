package com.gg.chattingservice.modules.chat.dto;

import java.util.List;
import lombok.Data;

@Data
public class ChatRoomInfo {

    private String accountId;

    private List<ChatRoomDto> chatRoomList;

}
