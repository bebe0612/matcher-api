package kr.kw.matcher.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/sub");  //api의 경로 맨 앞이 /sub일 경우 메시지가 브로커로 이동
        config.setApplicationDestinationPrefixes("/pub");  //메시지 발행 요청 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")//클라이언트에서 websocket에 접속하기 위한 endpoint ex)localhost:80/ws-stomp
                .withSockJS();
    }
}
