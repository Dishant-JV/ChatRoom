package com.chat.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.demo.modal.ChatRoom;
import com.chat.demo.repo.ChatRoomRepo;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Builder
public class ChatRoomService {

    private final ChatRoomRepo chatRoomRepo;

    public Optional<String> getChatRoomId(String senderId, String receipentId, boolean createRoomIfNotExists) {
        return chatRoomRepo.findBySenderIdAndRecipientId(receipentId, senderId).map(ChatRoom::getChatId)
                .or(() -> {
                    if (createRoomIfNotExists) {
                        var chatId = createChatId(senderId, receipentId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String receipentId) {
        var chatId = String.format("%s_%s", senderId.toString(), receipentId.toString());
        ChatRoom sender = new ChatRoom();
        ChatRoom receiver = new ChatRoom();

        // sender
        sender.setChatId(chatId);
        sender.setSenderId(senderId);
        sender.setRecipientId(receipentId);

        // receipient
        receiver.setChatId(chatId);
        receiver.setSenderId(receipentId);
        receiver.setRecipientId(senderId);

        chatRoomRepo.save(sender);
        chatRoomRepo.save(receiver);
        return chatId;
    }
}
