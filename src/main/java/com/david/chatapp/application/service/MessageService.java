package com.david.chatapp.application.service;

import com.david.chatapp.infraestructure.db.mongodb.MessageRepository;
import com.david.chatapp.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    public void saveMessage(Message message);
}
