package kr.kw.matcher.module.chat.chat.dto;

import lombok.Data;

@Data
public class ChatRequestMessage {
    private MessageType messageType;
    private String text;
}
