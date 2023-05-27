package com.gg.chattingservice.modules.chat.service;

import com.gg.chattingservice.modules.chat.dto.ChatMessage;
import com.gg.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.chattingservice.modules.chat.repository.chatroom.ChatRoomRepository;
import com.gg.chattingservice.modules.redis.RedisService;
import com.gg.commonservice.annotation.BaseServiceAnnotation;
import java.util.List;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class ChatService {

    private final RedisService redisService;
    private final ChatRoomRepository chatRoomRepository;
    public void sendMessage(ChatMessage message) {
        redisService.sendMessage(message);
    }

    public List<ChatRoom> getRooms(String accountId) {
        return chatRoomRepository.findAllRoom();
    }

    public ChatRoomDto createRoom(String roomName) {
        return redisService.createChatRoom(roomName);
    }

    public ChatRoomDto getRoom(String roomId) {
        return ChatRoomDto.from(chatRoomRepository.findRoomById(roomId));
    }

}
