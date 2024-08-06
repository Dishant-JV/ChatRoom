package com.chat.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.demo.modal.ChatMsg;
import com.chat.demo.repo.ChatMsgRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMsgService {

    private final ChatMsgRepo chatMsgRepo;
    private final ChatRoomService chatRoomService;

    public ChatMsg save(ChatMsg chatMsg) {
        var chatId = chatRoomService.getChatRoomId(chatMsg.getSenderId(), chatMsg.getRecipientId(), true).orElseThrow();
        chatMsg.setChatId(chatId);
        return chatMsgRepo.save(chatMsg);
    }

    public List<ChatMsg> listOfMsg(Long sId, Long rId) {
        var chatId = chatRoomService.getChatRoomId(sId, rId, false);
        return chatId.map(chatMsgRepo::findByChatId).orElse(new ArrayList<>());
    }
}
