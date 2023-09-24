package kr.kw.matcher.module.chat.chat_room.application;

import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoomRepository;
import kr.kw.matcher.module.chat.chat_room.repository.RedisChatRoomRepository;
import kr.kw.matcher.module.chat.chat_room_member.application.ChatRoomMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final RedisChatRoomRepository redisChatRoomRepository;
    private final ChatRoomMemberService chatRoomMemberService;

    @Transactional
    public ChatRoom createOne(Long userId) {
        ChatRoom willSavedChatRoom = ChatRoom.of(userId, "");
        willSavedChatRoom.setMemberCount(1L);

        ChatRoom chatRoom = chatRoomRepository.save(willSavedChatRoom);
        redisChatRoomRepository.save(chatRoom);

        // host user join
        chatRoomMemberService.createOne(chatRoom.getId(), userId);

        return chatRoom;
    }

    @Transactional
    public void joinRoom(Long roomId, Long userId) {
        //ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();
        ChatRoom chatRoom = redisChatRoomRepository.findRoomById(roomId.toString());
        chatRoom.setMemberCount(chatRoom.getMemberCount() + 1L);

        chatRoomMemberService.createOne(roomId, userId);
    }
}
