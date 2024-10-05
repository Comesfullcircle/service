package org.delivery.storeadmin.domain.sse.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {
    /**
     * SSE
     *
     * SSE(EventSource)는 “Server-Sent Events” 의 약어,
     *
     * 단방향 통신을 통해 서버에서 클라이언트로 실시간 이벤트를 전송하는 웹 기술.’ 이를 통해 서버 측에서 이벤트를 생성하고, 클라이언트는 이벤트를 실시간으로 수신할 수 있음
     *
     * 1. 일반적인 웹 소켓과 비교하면, SSE는 단방향 통신만을 지원하며, 추가적인 설정 없이도 웹 브라우저에서 내장된 기능으로 지원됨
     * 2. SSE는 서버에서 클라이언트로만 데이터를 전송하는 단방향 통신. 클라이언트는 서버로부터 이벤트를 수신하며, 서버로부터의 요청은 지원하지 않음
     * 3. SSE는 텍스트 기반 형식으로 데이터를 전송함. 이벤트는 data, event, id, retry 와 같은 필드로 구성된 텍스트 형태로 클라이언트로 전송됨
     * 4. SSE는 기존의 HTTP 연결을 재사용하여 데이터를 전송함. 따라서 별도의 특별한 프로토콜이나 서버 구성이 필요하지 않음
     *
     * HTTP 통신 방식: 요청이 있으면 응답을 받고 끝나는 방식
     * SSE 통신 방식: 최초에 한번 클라이언트가 접속 요청, 그에 대해 서버가 접속이 되었다는 응답을 줌 그렇게 되면 연결이 유지 또는 타임아웃이 되었더라도 서버가 언제든지 클라이언트 쪽으로 요청을 보낼 수 있음
     * 클라이언트가 서버로 요청하는것은 연결 접속밖에 없고, 데이터에 대한 통신은 없음 ( 단방향으로만 통신이 가능)
     * Web Socket 통신 방식: 최초에 접속을 하면 커넥션을 맺음 -> 커넥션이 맺어졌으면 그다음부터는 클라이언트가 서버로 데이터를 보낼 수 있고 서버가 클라이언트에게 데이터를 요청할 수 있음
     * 언제든지 보내고 받고 서로 유기적으로 요청을 계속 보낼수 있음
     */
    private static final Map<String, SseEmitter> userConnection = new ConcurrentHashMap<>();

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        log.info("login user {}", userSession);

        var emitter = new SseEmitter(1000L * 60); //ms 단위로 재연결 시킴
        userConnection.put(userSession.getUserId().toString(), emitter);

        emitter.onTimeout(()->{
            log.info("on timeout");
            //클라이언트와 타임아웃이 일어났을때
            emitter.complete();
        });

        emitter.onCompletion(()->{
            log.info("completion");
            //클라이언트와 연결이 종료 됐을때 하는 작업
            userConnection.remove(userSession.getUserId().toString());
        });

        //최초 연결시 응답 전송

        var event = SseEmitter
                .event()
                .name("onopen")
                ;

        try {
            emitter.send(event);
        } catch (IOException e){
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @GetMapping("/push-event")
    public void pushEvent(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
    ){
        // 기존에 연결된 유저 찾기
        var emitter = userConnection.get(userSession.getUserId().toString());

        var event = SseEmitter
                .event()
                .data("hello") //onmessage
                ;

        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
