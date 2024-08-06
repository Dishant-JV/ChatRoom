package com.chat.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.modal.ChatRoom;

public interface ChatRoomRepo extends JpaRepository<ChatRoom,Long>{
    
    Optional<ChatRoom> findBySenderIdAndRecipientId(Long sId,Long rId);
}
