package com.gg.chattingservice.modules.redis;

import com.gg.chattingservice.modules.chat.dto.ChatMessage;
import com.gg.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.chattingservice.modules.chat.enums.MessageType;
import com.gg.chattingservice.modules.chat.repository.chatroom.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final ChatRoomRepository chatRoomRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final RedisPublisher redisPublisher;

    public ChatRoomDto createChatRoom(String roomName) {
        ChatRoom chatRoom = ChatRoom.create(roomName);
        ChatRoomDto chatRoomDto = ChatRoomDto.from(chatRoom);
        redisTemplate.opsForList().rightPush(chatRoom.getRoomId(), chatRoomDto);
        return chatRoomDto;
    }

    public void sendMessage(ChatMessage chatMessage) {
        if (MessageType.ENTER.equals(chatMessage.getType())) {
            enterChatRoom(chatMessage.getRoomId());
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(chatRoomRepository.getTopic(chatMessage.getRoomId()), chatMessage);
    }

    public void enterChatRoom(String roomId) {
        ChannelTopic topic = new ChannelTopic(roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
    }

}
