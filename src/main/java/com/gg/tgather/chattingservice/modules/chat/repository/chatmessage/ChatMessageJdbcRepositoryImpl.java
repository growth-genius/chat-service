package com.gg.tgather.chattingservice.modules.chat.repository.chatmessage;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatMessageDto;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageJdbcRepositoryImpl implements ChatMessageJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsert(List<ChatMessageDto> chatMessageDtoList) {
        String sql = "insert into chat_message ("
            + "message_id, message_type, room_id, sender, message, time) "
            + " values ("
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ChatMessageDto chatMessageDto = chatMessageDtoList.get(i);
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, chatMessageDto.getMessageType().name());
                ps.setString(3, chatMessageDto.getRoomId());
                ps.setString(4, chatMessageDto.getSender());
                ps.setString(5, chatMessageDto.getMessage());
                ps.setObject(6, LocalDateTime.now().toString());
            }

            @Override
            public int getBatchSize() {
                return chatMessageDtoList.size();
            }
        });
    }
}
