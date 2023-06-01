package com.gg.tgather.chattingservice.modules.chat.repository.chataccount;

import com.gg.tgather.chattingservice.modules.chat.entity.ChatAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatAccountRepository extends JpaRepository<ChatAccount, Long> {}
