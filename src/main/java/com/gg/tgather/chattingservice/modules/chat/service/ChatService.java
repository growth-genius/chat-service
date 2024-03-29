package com.gg.tgather.chattingservice.modules.chat.service;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.repository.chatroom.ChatRoomRepository;
import com.gg.tgather.chattingservice.modules.redis.RedisService;
import com.gg.tgather.commonservice.annotation.BaseServiceAnnotation;
import com.gg.tgather.commonservice.security.JwtAuthentication;
import java.util.List;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class ChatService {

    private final RedisService redisService;
    private final ChatRoomRepository chatRoomRepository;
    public void sendMessage(ChatMessageDto message, JwtAuthentication authentication) {
        redisService.sendMessage(message, authentication);
    }

    public List<ChatRoomDto> getRooms(String accountId) {
        return chatRoomRepository.findAllRoomByAccountId(accountId).stream().map(ChatRoomDto::from).distinct().toList();
    }

    public ChatRoomDto createRoom(String roomName, JwtAuthentication authentication) {
        return redisService.createChatRoom(roomName, authentication);
    }

    public ChatRoomDto getRoom(String roomId) {
        return redisService.getChatRoom(roomId);
    }

    public ChatRoomDto joinRoom(String roomId, JwtAuthentication authentication) {
        return redisService.joinRoom(roomId, authentication);
    }
}
