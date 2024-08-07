package com.chat.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.demo.modal.Status;
import com.chat.demo.modal.User;

public interface UserRepo extends JpaRepository<User, Long>{
    List<User> findAllByStatus(Status status);

    List<User> findByFullName(String fullName);
} 
