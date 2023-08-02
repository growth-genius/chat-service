package com.gg.tgather.chattingservice.modules.redis;

import com.gg.tgather.chattingservice.infra.properties.ChatProperties;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatAccount;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatRoom;
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
        ChatRoomDto chatRoomDto = saveAndGetChatRoom(roomName, authentication);
        hashChatRoomDto.put(chatProperties.getRoomName(), chatRoomDto.getRoomId(), chatRoomDto);
        return chatRoomDto;
    }

    /**
     * 채팅방 저장 후 리턴
     * @param roomName 방 이름
     * @param authentication 로그인 사용자
     * @return ChatRoom
     */
    private ChatRoomDto saveAndGetChatRoom(String roomName, JwtAuthentication authentication) {
        ChatRoom chatRoom = ChatRoom.create(roomName);
        chatRoomRepository.save(chatRoom);
        ChatAccount chatAccount = ChatAccount.of(authentication.accountId(), chatRoom);
        chatAccountRepository.save(chatAccount);
        initChatRoom(chatRoom);
        joinRoom(chatRoom.getRoomId(), authentication);
        return ChatRoomDto.from(chatRoom);
    }

    /**
     * 채팅방 초기 세팅
     * @param chatRoom 만들어진 채팅방
     */
    private void initChatRoom(ChatRoom chatRoom) {
        String roomId = chatRoom.getRoomId();
        ChannelTopic topic = topics.getOrDefault(roomId,new ChannelTopic(roomId));
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(chatRoom.getRoomId(), topic);
    }

    /**
     * 메시지 전송
     * @param chatMessageDto 전송할 메시지 정보
     * @param authentication 로그인 사용자
     */
    public void sendMessage(ChatMessageDto chatMessageDto, JwtAuthentication authentication) {
        chatMessageDto.setAccountInfo(authentication);
        redisPublisher.publish(new ChannelTopic(chatMessageDto.getRoomId()), chatMessageDto);
    }

    public ChatRoomDto getChatRoom(String roomId) {
        return hashChatRoomDto.get(chatProperties.getRoomName(), roomId);
    }

    /**
     * 채팅방 입장
     * @param roomId 채팅방 식별자
     * @param authentication 로그인 사용자
     * @return ChatRoomDto
     */
    public ChatRoomDto joinRoom(String roomId, JwtAuthentication authentication) {
        ChatMessageDto chatMessageDto = ChatMessageDto.enterRoom(roomId, authentication.accountId(), authentication.nickname());
        ChannelTopic topic = topics.getOrDefault(roomId, new ChannelTopic(roomId));
        redisPublisher.publish(topic, chatMessageDto);
        return getChatRoom(roomId);
    }

}
