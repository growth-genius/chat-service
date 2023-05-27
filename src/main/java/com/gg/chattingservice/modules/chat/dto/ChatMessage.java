package com.gg.chattingservice.modules.chat.dto;

import com.gg.chattingservice.modules.chat.enums.MessageType;
import lombok.Data;

@Data
public class ChatMessage {

    /** 메시지 유형 */
    private MessageType type;
    /** 방번호 */
    private String roomId;
    /** 보낸 사람 */
    private String sender;
    /** 메시지 내용 */
    private String message;
    /** 시간 */
    private String time;

}
