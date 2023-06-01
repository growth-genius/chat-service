package com.gg.tgather.chattingservice.modules.chat.repository.chatroom;

import static com.gg.tgather.chattingservice.modules.chat.entity.QChatAccount.chatAccount;
import static com.gg.tgather.chattingservice.modules.chat.entity.QChatRoom.chatRoom;
import static com.querydsl.core.types.Projections.constructor;

import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomDto;
import com.gg.tgather.chattingservice.modules.chat.dto.ChatRoomSearchDto;
import com.gg.tgather.chattingservice.modules.chat.entity.ChatRoom;
import com.gg.tgather.commonservice.jpa.Querydsl5Support;
import java.util.List;
import java.util.Optional;

public class ChatRoomRepositoryImpl extends Querydsl5Support implements ChatRoomRepositoryQuerydsl {

    protected ChatRoomRepositoryImpl() {
        super(ChatRoom.class);
    }


    @Override
    public List<ChatRoomSearchDto> findAllRoomByAccountId(String accountId) {
        return select(constructor(ChatRoomSearchDto.class, chatRoom, chatAccount, chatRoom.accountIds.size())).from(chatRoom).leftJoin(chatAccount).on(chatAccount.chatRoom.eq(chatRoom)).fetchJoin().where(chatAccount.accountId.eq(accountId)).fetch();
    }

    @Override
    public Optional<ChatRoomDto> findRoomById(String roomId) {
        return Optional.ofNullable(select(constructor(ChatRoomDto.class, chatRoom)).from(chatRoom).where(chatRoom.roomId.eq(roomId)).fetchOne());
    }
}
