package kr.kw.matcher.module.chat.chat_room.domain;

import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.ChatRepository;
import kr.kw.matcher.module.chat.chat_room_member.domain.ChatRoomMember;
import kr.kw.matcher.module.chat.chat_room_member.domain.ChatRoomMemberRepository;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class ChatRoomRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired ChatRoomRepository chatRoomRepository;
    @Autowired ChatRepository chatRepository;
    @Autowired ChatRoomMemberRepository chatRoomMemberRepository;
    @DisplayName("insert & select test")
    @Test
    void insertAndSelect(){
        //given
        Pageable pageable = Pageable.ofSize(50);
        User user1 = userRepository.save(User.of(1L));
        User user2 = userRepository.save(User.of(2L));

        ChatRoom chatRoom1 = chatRoomRepository.save(ChatRoom.of(user1.getId(), "chatRoom1"));
        ChatRoom chatRoom2 = chatRoomRepository.save(ChatRoom.of(user1.getId(), "chatRoom1"));

        chatRoomMemberRepository.save(ChatRoomMember.of(1L, 1L));
        chatRoomMemberRepository.save(ChatRoomMember.of(1L, 2L));
        chatRoomMemberRepository.save(ChatRoomMember.of(2L, 1L));
        chatRoomMemberRepository.save(ChatRoomMember.of(2L, 2L));

        Chat chat1 = chatRepository.save(Chat.of(chatRoom1.getId(), user1.getId(), "message1"));
        Chat chat2 = chatRepository.save(Chat.of(chatRoom1.getId(), user2.getId(), "message2"));

        //when
        Optional<Chat> chatTest1 = chatRepository.findById(chat1.getId());
        Optional<ChatRoom> chatRoomTest1 = chatRoomRepository.findById(chatRoom1.getId());

        //then
        assertThat(chatRoomRepository.count()).isEqualTo(2);
        assertThat(chatRepository.count()).isEqualTo(2);
        assertThat(chatRoomTest1.get()).isEqualTo(chatRoom1);
        assertThat(chatTest1.get()).isEqualTo(chat1);
        assertThat(chatRepository.findByRoomId(chatRoom1.getId(), pageable)).contains(chat1, chat2);
        assertThat(chatRoomRepository.findAllRoomByUserId(1L)).hasSize(2);
    }

    @DisplayName("update test")
    @Test
    void updateTest(){
        //given
        User user1 = userRepository.save(User.of(1L));
        User user2 = userRepository.save(User.of(2L));
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.of(user1.getId(), "chatRoom1"));
        Chat chat1 = chatRepository.save(Chat.of(chatRoom.getId(), user1.getId(), "message1"));
        Chat chat2 = chatRepository.save(Chat.of(chatRoom.getId(), user2.getId(), "message2"));

        //when
        chatRoom.setName("changed name");
        chat1.setText("changed text1");


        //then
        assertThat(chatRoom).hasFieldOrPropertyWithValue("name", "changed name");
        assertThat(chat1).hasFieldOrPropertyWithValue("text",  "changed text1");
    }

    @DisplayName("delete test")
    @Test
    void deleteTest(){
        //given
        User user1 = userRepository.save(User.of(1L));
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.of(user1.getId(), "chatRoom1"));
        Chat chat1 = chatRepository.save(Chat.of(chatRoom.getId(), user1.getId(), "message1"));

        //when
        chatRoomRepository.delete(chatRoom);
        chatRepository.delete(chat1);

        //then
        assertThat(chatRepository.count()).isEqualTo(0);
        assertThat(chatRoomRepository.count()).isEqualTo(0);
    }
}