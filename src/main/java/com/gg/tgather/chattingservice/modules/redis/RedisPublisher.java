package com.gg.tgather.chattingservice.modules.redis;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageDto message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}