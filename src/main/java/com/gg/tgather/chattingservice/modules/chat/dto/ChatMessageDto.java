package com.gg.tgather.chattingservice.modules.chat.dto;

import com.gg.tgather.commonservice.security.JwtAuthentication;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 채팅 메시지 dto
 * Redis 에 저장되는 항목.
 * nickname 과 accountId 는 로그인 사용자의 정보를 세팅한다.
 * @author seunggu.lee
 * @since 2023.06.11
 * @version 0.0.1
 */
@Data
@NoArgsConstructor
public class ChatMessageDto {

    /** 방번호 */
    private String roomId;
    /** 보낸 사람 */
    private String nickname;
    /** 메시지 내용 */
    private String message;
    /** 사용자 아이디 */
    private String accountId;
    /** 시간 */
    private LocalDateTime time;

    public ChatMessageDto(String roomId, String accountId, String nickname) {
        this.roomId = roomId;
        this.accountId = accountId;
        this.nickname = nickname;
        this.time = LocalDateTime.now();
        this.message = this.nickname + " 님이 입장하셨습니다.";
    }

    public static ChatMessageDto enterRoom(String roomId, String accountId, String nickname) {
        return new ChatMessageDto(roomId, accountId, nickname);
    }

    /**
     * 로그인 사용자의 정보를 세팅
     * @param authentication 로그인 사용자
     */
    public void setAccountInfo(JwtAuthentication authentication) {
        this.nickname = authentication.nickname();
        this.accountId = authentication.accountId();
    }

}
