package kr.kw.matcher.module.chat.chat.presentation.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChatCreateBody {
    @NotNull
    private Long roomId;
    @NotNull
    private Long userId;
    @NotNull
    private String text;
}
