package com.test.springboot.config.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserQueueSender sender;

    public void save(UserModel um){
        this.sender.sendMsg(um,"OP_Type_Add");
    }
}
