package com.gg.tgather.chattingservice.modules.chat.service;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.repository.chatroom.ChatRoomRepository;
import com.gg.tgather.chattingservice.modules.redis.RedisService;
import com.gg.tgather.commonservice.annotation.BaseServiceAnnotation;
import java.util.List;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class ChatService {

    private final RedisService redisService;
    private final ChatRoomRepository chatRoomRepository;
    public void sendMessage(ChatMessageDto message) {
        redisService.sendMessage(message);
    }

    public List<ChatRoomDto> getRooms(String accountId) {
        return chatRoomRepository.findAllRoomByAccountId(accountId).stream().map(ChatRoomDto::from).distinct().toList();
    }

    public ChatRoomDto createRoom(String roomName) {
        return redisService.createChatRoom(roomName);
    }

    public ChatRoomDto getRoom(String roomId) {
        return chatRoomRepository.findRoomById(roomId).orElseThrow(()-> new RuntimeException("일치하는 방이 없습니다."));
    }

}
