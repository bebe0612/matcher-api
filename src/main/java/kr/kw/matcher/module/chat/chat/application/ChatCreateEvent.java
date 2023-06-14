package kr.kw.matcher.module.chat.chat.application;

import kr.kw.matcher.module.chat.chat.application.dto.ChatDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatCreateEvent {
    private ChatDto chatDto;
}
