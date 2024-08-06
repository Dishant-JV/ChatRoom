package com.chat.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.demo.modal.Status;
import com.chat.demo.modal.User;
import com.chat.demo.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepo userRepo;
    
    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        userRepo.save(user);
    }

    public void disconnect(User user){
        var storedUser = userRepo.findById(user.getId()).orElse(null);
        if(storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            userRepo.save(storedUser);
        }
    }

    public List<User> findConnectedUser(){
        return userRepo.findAllByStatus(Status.ONLINE);
    }
}