package kr.kw.matcher.module.chat.chat.application.dto;

import com.campusnow.api.module.chat.chat.domain.ChatType;
import com.campusnow.api.module.chat.chat.domain.QChat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatBriefDto {
    private Long id;

    private Long userId;

    private Long seqId;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDt;

    private ChatType chatType;

    private String text;

    public static QBean<ChatBriefDto> getBean(QChat chat) {
        return Projections.bean(ChatBriefDto.class,
                chat.id,
                chat.userId,
                chat.seqId,
                chat.createdDt,
                chat.chatType,
                chat.text);
    }
}
