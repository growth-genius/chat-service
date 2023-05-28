package com.gg.chattingservice.modules.chat;


import com.gg.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.chattingservice.modules.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RequiredArgsConstructor
public class ChatController {


    private final ChatService chatService;


    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {
        chatService.sendMessage(message);
    }

}
