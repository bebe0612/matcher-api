package kr.kw.matcher.module.chat.chat_room.application;


import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoomRepository;
import kr.kw.matcher.module.user.application.UserService;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
/*
@Slf4j
@DisplayName("비즈니스 로직 - 채팅방")
@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {
    @InjectMocks UserService userService;
    @InjectMocks ChatRoomService chatRoomService;
    @Mock ChatRoomRepository chatRoomRepository;
    @Mock UserRepository userRepository;

    @DisplayName("채팅방을 생성하고 생성한 유저 확인")
    @Test
    void create_ChatRoom_And_FindHost(){
        //given
        //User user = userService.createOne();
        User  user = User.of(1L);
        //given(userRepository.save(user)).willReturn()
        lenient().when(userRepository.save(any(User.class))).thenReturn(user);
        lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        ChatRoom chatRoom = ChatRoom.of(1L, user.getId(), user.getNickname());
        lenient().when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);
        //lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        //when
        chatRoomService.init();
        ChatRoom chatRoom2  = chatRoomService.createOne(user.getId());

        //then
        //assertEquals(chatRoom.getHostId(), user.getId());
    }
}
    */
