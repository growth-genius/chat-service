package com.gg.tgather.chattingservice.modules.redis;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.tgather.chattingservice.modules.chat.enums.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

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

    public void sendMessage(ChatMessageDto chatMessageDto) {
        if (MessageType.ENTER.equals(chatMessageDto.getMessageType())) {
            enterChatRoom(chatMessageDto.getRoomId());
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(new ChannelTopic(chatMessageDto.getRoomId()), chatMessageDto);
    }

    public void enterChatRoom(String roomId) {
        ChannelTopic topic = new ChannelTopic(roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
    }

}
