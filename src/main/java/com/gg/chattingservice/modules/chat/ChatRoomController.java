package com.gg.chattingservice.modules.chat;

import static com.gg.commonservice.utils.ApiUtil.success;

import com.gg.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.chattingservice.modules.chat.dto.ChatRoomInfo;
import com.gg.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.chattingservice.modules.chat.service.ChatService;
import com.gg.commonservice.annotation.RestBaseAnnotation;
import com.gg.commonservice.security.JwtAuthentication;
import com.gg.commonservice.utils.ApiUtil.ApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
        return success(chatService.getRooms(authentication.accountId()));
    }

    @PostMapping("/room/{roomName}")
    public ApiResult<ChatRoomDto> createRoom(@PathVariable String roomName) {
        return success(chatService.createRoom(roomName));
    }

    @GetMapping("/room/{roomId}")
    public ApiResult<ChatRoomDto> roomInfo(@PathVariable String roomId) {
        return success(chatService.getRoom(roomId));
    }
}
