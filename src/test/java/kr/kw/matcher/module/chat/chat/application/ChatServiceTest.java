package kr.kw.matcher.module.chat.chat.application;

import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.ChatRepository;
import kr.kw.matcher.module.chat.chat.dto.ChatDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Slf4j
@DisplayName("비즈니스 로직 - 채팅 메시지")
@ExtendWith(MockitoExtension.class)
class ChatServiceTest {
    @InjectMocks private ChatService chatService;
    @Mock private ChatRepository chatRepository;


    @DisplayName("채팅방 id를 이용해서 chatdto page 가져오기")
    @Test
    void findChatByRoomId(){
        //given
        Pageable pageable = Pageable.ofSize(50);
        List<Chat> list = new ArrayList<>();
        list.add(Chat.of(1L,1L, "text1"));
        list.add(Chat.of(1L, 2L, "text2"));
        Page<Chat> page = new PageImpl<>(list);
        given(chatRepository.findByRoomId(1L, pageable)).willReturn(page);

        //when
        Page<ChatDto> pageDto = chatService.findChatsWithRoomId(1L, pageable);

        //then
        assertThat(pageDto).hasSize(2);
    }

}