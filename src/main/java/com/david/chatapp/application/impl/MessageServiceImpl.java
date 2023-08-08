package com.david.chatapp.application.impl;

import com.david.chatapp.application.service.MessageService;
import com.david.chatapp.domain.Message;
import com.david.chatapp.infraestructure.db.mongodb.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository repository;


    public void saveMessage(Message message){
        repository.save(message);
    }
}
