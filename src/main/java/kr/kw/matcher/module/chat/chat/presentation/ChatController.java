package kr.kw.matcher.module.chat.chat.presentation;

import kr.kw.matcher.module.chat.chat.application.ChatService;
import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.presentation.request.ChatCreateBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatCreateBody body) {
        chatService.sendMessage(body);
    }

    @GetMapping("/v1/chats")
    public List<Chat> getChats(@RequestParam Long roomId) {
        return chatService.getChats(roomId);
    }
}
