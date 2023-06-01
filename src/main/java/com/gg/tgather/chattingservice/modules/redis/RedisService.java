package com.gg.tgather.chattingservice.modules.redis;

import com.gg.tgather.chattingservice.infra.properties.ChatProperties;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatAccount;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.tgather.chattingservice.modules.chat.enums.MessageType;
import com.gg.tgather.chattingservice.modules.chat.repository.chataccount.ChatAccountRepository;
import com.gg.tgather.chattingservice.modules.chat.repository.chatroom.ChatRoomRepository;
import com.gg.tgather.commonservice.security.JwtAuthentication;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListener;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatAccountRepository chatAccountRepository;
    private final RedisSubscriber redisSubscriber;
    private final RedisPublisher redisPublisher;
    private final ChatProperties chatProperties;
    private final HashOperations<String, String, ChatRoomDto> hashChatRoomDto;
    private final Map<String, ChannelTopic> topics = new ConcurrentHashMap<>();

    public RedisService(RedisTemplate<String, Object> redisTemplate, RedisMessageListenerContainer redisMessageListener, ChatRoomRepository chatRoomRepository,
        ChatAccountRepository chatAccountRepository, RedisSubscriber redisSubscriber, RedisPublisher redisPublisher, ChatProperties chatProperties) {
        this.redisTemplate = redisTemplate;
        this.redisMessageListener = redisMessageListener;
        this.chatRoomRepository = chatRoomRepository;
        this.chatAccountRepository = chatAccountRepository;
        this.redisSubscriber = redisSubscriber;
        this.redisPublisher = redisPublisher;
        this.chatProperties = chatProperties;
        this.hashChatRoomDto = redisTemplate.opsForHash();
    }

    @Transactional
    public ChatRoomDto createChatRoom(String roomName, JwtAuthentication authentication) {
        ChatRoom chatRoom = saveAndGetChatRoom(roomName, authentication);
        ChatRoomDto chatRoomDto = ChatRoomDto.from(chatRoom);
        hashChatRoomDto.put(chatProperties.getRoomName(), chatRoom.getRoomId(), chatRoomDto);
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
        ChannelTopic topic = topics.getOrDefault(roomId,new ChannelTopic(roomId));
        redisMessageListener.addMessageListener(redisSubscriber, topic);
    }

    public ChatRoomDto getChatRoom(String roomId) {
        return hashChatRoomDto.get(chatProperties.getRoomName(), roomId);
    }

}
