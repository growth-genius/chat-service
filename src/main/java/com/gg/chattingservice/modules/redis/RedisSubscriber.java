package com.gg.chattingservice.modules.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.chattingservice.modules.chat.dto.ChatMessage;
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
    private final RedisPublisher redisPublisher;
    private final int batchSize = 1000;  // 데이터베이스에 적재할 배치 크기
    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     */
    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        try {
            // redis 에서 발행된 데이터를 받아 deserialize
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객채로 맵핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // Websocket 구독자에게 채팅 메시지 Send
            String roomId = roomMessage.getRoomId();
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, roomMessage);
            // 데이터베이스에 적재할 배치 크기가 쌓일 때마다 데이터베이스에 적재
            Long listSize = redisTemplate.opsForList().size(roomId);
            if (listSize != null && (listSize % batchSize == 0)) {
                saveToDatabase(roomId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void saveToDatabase(String channel) {
        // 데이터베이스에 적재하는 로직 수행
        System.out.println("Saving data from channel " + channel + " to the database...");

        // 적재한 데이터를 Redis에서 삭제
        redisTemplate.delete(channel);
    }


}