package com.chat.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.chat.demo.modal.Status;
import com.chat.demo.modal.User;
import com.chat.demo.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepo userRepo;
    
    // public User saveUser(User user){
    //     List<User> ls = userRepo.findByFullName(user.getFullName());
    //     if(ls.size() == 0){
    //         userRepo.save(user);
    //         return user;
    //     }else{
    //         return ls.get(0);
    //     }
    // }

    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        userRepo.save(user);
    }

    public void disconnect(User user){
        var storedUser = userRepo.findById((Long)user.getId()).orElse(null);
        if(storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            userRepo.save(storedUser);
        }
    }

    public List<User> findConnectedUser(){
        return userRepo.findAllByStatus(Status.ONLINE);
    }
}
