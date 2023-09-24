package kr.kw.matcher.module.chat.chat.presentation;

import kr.kw.matcher.module.chat.chat.application.ChatService;
import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.presentation.request.ChatCreateBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatCreateBody body) {
        chatService.sendMessage(body);
    }
}
