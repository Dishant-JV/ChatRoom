package com.chat.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.modal.ChatMsg;
import java.util.List;


public interface ChatMsgRepo extends JpaRepository<ChatMsg,Long>{
    List<ChatMsg> findByChatId(String chatId);
}
