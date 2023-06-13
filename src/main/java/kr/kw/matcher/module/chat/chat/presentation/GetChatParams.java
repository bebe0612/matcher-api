package kr.kw.matcher.module.chat.chat.presentation;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GetChatParams {

    private Long startSeqId;

    private Long lastSeqId;

    private Long limit;
}