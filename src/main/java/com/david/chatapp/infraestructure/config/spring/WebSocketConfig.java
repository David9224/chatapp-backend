package com.david.chatapp.infraestructure.config.spring;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@CrossOrigin
@Log4j2
public class WebSocketConfig {
    //@Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;
    private SocketIOServer server;

    public WebSocketConfig() throws UnknownHostException {
        this.host = InetAddress.getLocalHost().getHostAddress();
    }

    @Bean
    public SocketIOServer socketIOServer(){
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        server = new SocketIOServer(config);
        server.start();
        return server;
    }

    @PreDestroy
    public void stopSocketIOServer(){
        this.server.stop();
    }
}
