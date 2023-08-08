package com.david.chatapp.application.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.david.chatapp.application.service.MessageService;
import com.david.chatapp.domain.Message;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Log4j2
@Component
public class SocketModule {
    @Autowired
    private SocketIOServer server;
    @Autowired
    private MessageService service;

    public SocketModule(SocketIOServer server) {
        this.server = server;
        server.addConnectListener(onUserConnectWithSocket);
        server.addDisconnectListener(onUserDisconnectWithSocket);
        server.addEventListener("send_message", Message.class, onSendMessage);

    }

    public ConnectListener onUserConnectWithSocket = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {
            log.info("Socket ID[{}] Connected to socket ", client.getSessionId().toString());
        }
    };

    public DisconnectListener onUserDisconnectWithSocket = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
            client.getNamespace().getAllClients().stream().forEach(data-> {
                log.info("user disconnected "+data.getSessionId().toString());});
        }
    };

    public DataListener<Message> onSendMessage = new DataListener<Message>() {
        @Override
        public void onData(SocketIOClient client, Message message, AckRequest acknowledge) throws Exception {

            /**
             * Sending message to target user
             * target user should subscribe the socket event with his/her name.
             * Send the same payload to user
             */

            log.info(message.getSenderName()+" user send message to user "+message.getTargetUserName()+" and message is "+message.getMessage());
            server.getBroadcastOperations().sendEvent(message.getTargetUserName(), client, message);
            service.saveMessage(message);


            /**
             * After sending message to target user we can send acknowledge to sender
             */
            acknowledge.sendAckData("Message send to target user successfully");
        }
    };
}
