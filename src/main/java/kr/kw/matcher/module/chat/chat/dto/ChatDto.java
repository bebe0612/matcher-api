package kr.kw.matcher.module.chat.chat.dto;

import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private Long id;
    private MessageType type; // 메시지 타입
    private Long roomId;
    private Long userId;
    private String text;
    public static ChatDto of(Long id, MessageType type, Long roomId, Long userId, String text){
        return ChatDto.builder()
                .id(id)
                .type(type)
                .roomId(roomId)
                .userId(userId)
                .text(text)
                .build();
    }
    public Chat toEntity(){
        return Chat.of(roomId, userId, text);
    }
    public static ChatDto from(Chat entity){
        return ChatDto.of(entity.getId(), MessageType.TALK, entity.getRoomId(), entity.getUserId(), entity.getText());
    }
}
