package kr.kw.matcher.module.chat.chat.presentation.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChatGetParams {
    private Long offsetSeqId;
    private Boolean gt;
    @NotNull
    private Long limit;
}
