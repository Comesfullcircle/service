package org.delivery.storeadmin.domain.sse.connection.model;

import lombok.*;
import org.delivery.storeadmin.domain.sse.connection.Ifs.ConnectionPoolIfs;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Data
public class UserSseConnection {


    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private UserSseConnection(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs
    ){
        // key 초기화
        this.uniqueKey = uniqueKey;

        // sse 초기화
        this.sseEmitter = new SseEmitter(60*1000L);

        // call back 초기화
        this.connectionPoolIfs = connectionPoolIfs;

        //on completion
        this.sseEmitter.onCompletion(()->{
            // connection pool remove
           this.connectionPoolIfs.onCompletionCallback(this);
        });

        //on timeout
        this.sseEmitter.onTimeout(()->{
            this.sseEmitter.complete();
        });

        // onopen 메시지
        this.sendMessage("onopen", "connect");

    }

    public static UserSseConnection conect(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs
    ){
        return new UserSseConnection(uniqueKey, connectionPoolIfs);
    }

    public void sendMessage(String eventName, Object data) {

        var event = SseEmitter.event()
                .name(eventName)
                .data(data)
                ;

        try {
            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }

    }
}
