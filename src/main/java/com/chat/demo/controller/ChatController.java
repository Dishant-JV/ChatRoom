package com.chat.demo.controller;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.modal.ChatMsg;
import com.chat.demo.modal.ChatNotification;
import com.chat.demo.service.ChatMsgService;

import lombok.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMsgService chatMsgService;

    @MessageMapping("/chat")
    public void processMsg(@Payload ChatMsg chatMsg){
        ChatMsg savedMsg = chatMsgService.save(chatMsg);
        simpMessagingTemplate.convertAndSendToUser(
            chatMsg.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        savedMsg.getId().toString(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );

        // ChatMsg savedMsg = chatMsgService.save(chatMsg);
        // ChatNotification chatNotification = new ChatNotification();
        // chatNotification.setId(savedMsg.getId());
        // chatNotification.setSenderId(savedMsg.getSenderId());
        // chatNotification.setRecipientId(savedMsg.getRecipientId());
        // chatNotification.setContent(savedMsg.getContent());
        // simpMessagingTemplate.convertAndSendToUser(chatMsg.getRecipientId().toString(), "/queue/messages", chatNotification);
    }

    @GetMapping("messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMsg>> getMethodName(@PathVariable String senderId,@PathVariable String receiverId) {
        return ResponseEntity.ok(chatMsgService.listOfMsg(senderId, receiverId));
    }
    
}
