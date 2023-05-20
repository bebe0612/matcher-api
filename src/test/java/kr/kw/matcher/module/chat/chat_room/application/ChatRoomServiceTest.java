package kr.kw.matcher.module.chat.chat_room.application;


import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoomRepository;
import kr.kw.matcher.module.user.application.UserService;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
/*

@Slf4j
@DisplayName("비즈니스 로직 - 채팅방")
@DataJpaTest
class ChatRoomServiceTest {
    @InjectMocks UserService userService;
    @InjectMocks ChatRoomService chatRoomService;
    @Mock UserRepository userRepository;
    @Mock ChatRoomRepository chatRoomRepository;
    @DisplayName("채팅방을 생성하고 생성한 유저 확인")
    @Test
    void create_ChatRoom_And_FindHost(){
        //given
        User user = userService.createOne();

        //when
        ChatRoom chatRoom  = chatRoomService.createOne(user.getId());

        //then
        assertEquals(chatRoom.getHostId(), user.getId());
    }
}
*/