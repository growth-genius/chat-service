package com.gg.tgather.chattingservice.modules.redis;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatAccount;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.tgather.chattingservice.modules.chat.enums.MessageType;
import com.gg.tgather.chattingservice.modules.chat.repository.chataccount.ChatAccountRepository;
import com.gg.tgather.chattingservice.modules.chat.repository.chatroom.ChatRoomRepository;
import com.gg.tgather.commonservice.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListener;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatAccountRepository chatAccountRepository;
    private final RedisSubscriber redisSubscriber;
    private final RedisPublisher redisPublisher;

    @Transactional
    public ChatRoomDto createChatRoom(String roomName, JwtAuthentication authentication) {
        ChatRoom chatRoom = saveAndGetChatRoom(roomName, authentication);
        ChatRoomDto chatRoomDto = ChatRoomDto.from(chatRoom);
        redisTemplate.opsForList().rightPush(chatRoom.getRoomId(), chatRoomDto);
        return chatRoomDto;
    }

    @NotNull
    private ChatRoom saveAndGetChatRoom(String roomName, JwtAuthentication authentication) {
        ChatRoom chatRoom = ChatRoom.create(roomName);
        chatRoomRepository.save(chatRoom);
        ChatAccount chatAccount = ChatAccount.of(authentication.accountId(), chatRoom);
        chatAccountRepository.save(chatAccount);
        return chatRoom;
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
