package com.gg.tgather.chattingservice.modules.chat.controller;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.service.ChatService;
import com.gg.tgather.commonservice.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 메시지 전송
     * @param message 메시지
     * @param authentication 로그인 사용자
     */
    @MessageMapping("/message")
    public void message(ChatMessageDto message, @AuthenticationPrincipal JwtAuthentication authentication) {
        chatService.sendMessage(message, authentication);
    }


}
