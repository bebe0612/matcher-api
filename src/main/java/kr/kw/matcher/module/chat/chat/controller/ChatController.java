package kr.kw.matcher.module.chat.chat.controller;

import kr.kw.matcher.module.chat.chat.application.ChatService;
import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.ChatRepository;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.chat_room.repository.RedisChatRoomRepository;
import kr.kw.matcher.module.chat.redis.RedisPublisher;
import kr.kw.matcher.module.chat.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(Chat chat){
        chatService.sendMessage(chat);
    }

}
