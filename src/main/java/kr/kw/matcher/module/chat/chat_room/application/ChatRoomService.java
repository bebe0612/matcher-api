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

    public Long getChatRoomId(Long user1Id, Long user2Id) {
        Long hostId = Math.min(user1Id, user2Id);
        Long friendId = Math.max(user1Id, user2Id);
        ChatRoom chatRoom = chatRoomRepository.findByHostIdAndFriendId(hostId, friendId).orElseThrow();

        return chatRoom.getId();
    }
    @Transactional
    public ChatRoom createOne(Long userId, Long user2Id) {
        Long hostId = Math.min(userId, user2Id);
        Long friendId = Math.max(userId, user2Id);

        ChatRoom willSavedChatRoom = ChatRoom.of(hostId, friendId, "");

        ChatRoom chatRoom = chatRoomRepository.save(willSavedChatRoom);
        redisChatRoomRepository.save(chatRoom);

        return chatRoom;
    }
}
