package com.gg.tgather.chattingservice.modules.chat.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.gg.tgather.chattingservice.modules.chat.entity.ChatRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {

    private Long id;

    private String roomId;
    private String roomName;


    protected ChatRoomDto (ChatRoom chatRoom) {
        copyProperties(chatRoom, this);
    }
    public static ChatRoomDto from(ChatRoom chatRoom) {
        return new ChatRoomDto(chatRoom);
    }

    private ChatRoomDto() {}

    public static ChatRoomDto from(ChatRoomSearchDto chatRoomSearchDto) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        copyProperties(chatRoomSearchDto, chatRoomDto);
        return chatRoomDto;
    }

}
