package com.gg.chattingservice.modules.chat.dto;

import com.gg.chattingservice.modules.chat.enums.MessageType;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ChatMessageDto {

    /** 메시지 유형 */
    private MessageType messageType;
    /** 방번호 */
    private String roomId;
    /** 보낸 사람 */
    private String sender;
    /** 메시지 내용 */
    private String message;
    /** 시간 */
    private LocalDateTime time;

}
