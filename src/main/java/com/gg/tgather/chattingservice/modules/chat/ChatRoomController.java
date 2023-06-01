package com.gg.tgather.chattingservice.modules.chat;

import static com.gg.tgather.commonservice.utils.ApiUtil.success;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomInfo;
import com.gg.tgather.chattingservice.modules.chat.service.ChatService;
import com.gg.tgather.commonservice.annotation.RestBaseAnnotation;
import com.gg.tgather.commonservice.security.JwtAuthentication;
import com.gg.tgather.commonservice.utils.ApiUtil.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestBaseAnnotation
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    @GetMapping("/room")
    public ApiResult<ChatRoomInfo> rooms(@AuthenticationPrincipal JwtAuthentication authentication) {
        return success(ChatRoomInfo.builder().accountId(authentication.accountId()).chatRoomList(chatService.getRooms(authentication.accountId())).build());
    }

    @PostMapping("/room/{roomName}")
    public ApiResult<ChatRoomDto> createRoom(@PathVariable String roomName, @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(chatService.createRoom(roomName, authentication));
    }

    @GetMapping("/room/{roomId}")
    public ApiResult<ChatRoomDto> roomInfo(@PathVariable String roomId) {
        return success(chatService.getRoom(roomId));
    }

    @MessageMapping("/message")
    public void message(ChatMessageDto message) {
        chatService.sendMessage(message);
    }

}
