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

    /**
     * 사용자가 속해 있는 채팅 방 조회
     * @param authentication 사용자 정보
     * @return ChatRoomInfo 채팅 방 정보
     */
    @GetMapping("/rooms")
    public ApiResult<ChatRoomInfo> rooms(@AuthenticationPrincipal JwtAuthentication authentication) {
        return success(ChatRoomInfo.builder().accountId(authentication.accountId()).chatRoomList(chatService.getRooms(authentication.accountId())).build());
    }

    /**
     * 채팅 방 만들기
     * @param roomName 채팅방 이름
     * @param authentication 로그인 사용자 정보
     * @return ChatRoomDto 만들어진 채팅 방
     */
    @PostMapping("/rooms/room-name/{roomName}")
    public ApiResult<ChatRoomDto> createRoom(@PathVariable String roomName, @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(chatService.createRoom(roomName, authentication));
    }

    /**
     * 채팅방 입장
     * @param roomId 채팅방 식별자
     * @param authentication 로그인 사용자
     * @return ChatRoomDto 들어간 채팅방 정보
     */
    @PostMapping("/rooms/room-id/{roomId}")
    public ApiResult<ChatRoomDto> joinRoom(@PathVariable String roomId, @AuthenticationPrincipal JwtAuthentication authentication) {
        return success(chatService.joinRoom(roomId, authentication));
    }

    /**
     * 채팅방 정보
     * @param roomId 채팅방 식별자
     * @return ChatRoomDto 들아간 채팅방 정보
     */
    @GetMapping("/rooms/{roomId}")
    public ApiResult<ChatRoomDto> roomInfo(@PathVariable String roomId) {
        return success(chatService.getRoom(roomId));
    }

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
