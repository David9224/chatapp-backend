package com.david.chatapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "chatapp-messages")
public class Message {

    @Id
    private String _id;
    private MessageType type;
    private String message;
    private String room;
    private String senderName;
    private String targetUserName;


}
