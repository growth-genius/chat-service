package com.gg.chattingservice.modules.chat.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.gg.chattingservice.modules.chat.entity.ChatRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {

    private Long id;

    private String roomId;
    private String roomName;

    private long userCount;

    public static ChatRoomDto from(ChatRoom chatRoom) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        copyProperties(chatRoom, chatRoomDto);
        return chatRoomDto;
    }

    private ChatRoomDto() {}

}
