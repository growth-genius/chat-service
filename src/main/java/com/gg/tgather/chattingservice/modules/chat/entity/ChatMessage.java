package com.gg.tgather.chattingservice.modules.chat.entity;

import com.gg.tgather.chattingservice.modules.chat.enums.MessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class ChatMessage {

    @Id
    private String messageId;
    /** 메시지 유형 */
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    /** 방번호 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;
    /** 보낸 사람 */
    private String sender;
    /** 메시지 내용 */
    private String message;
    /** 시간 */
    private LocalDateTime time;

}
