package com.gg.tgather.chattingservice.modules.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;
    /**
     * Redis 에서 메시지가 발행(publish)되면 대기하고 있던 onMessage 가 해당 메시지를 받아 처리한다.
     */
    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        try {
            // redis 에서 발행된 데이터를 받아 deserialize
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객채로 맵핑
            ChatMessageDto roomMessage = objectMapper.readValue(publishMessage, ChatMessageDto.class);
            // Websocket 구독자에게 채팅 메시지 Send
            String roomId = roomMessage.getRoomId();
            messagingTemplate.convertAndSend("/sub/chat/rooms/" + roomId, roomMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}