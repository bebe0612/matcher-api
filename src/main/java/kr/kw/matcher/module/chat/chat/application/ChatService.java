package kr.kw.matcher.module.chat.chat.application;

import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.ChatRepository;
import kr.kw.matcher.module.chat.chat.dto.ChatDto;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoomRepository;
import kr.kw.matcher.module.chat.chat_room.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    @Transactional
    public Chat createOne(Long roomId, Long userId, String text){
        Chat chatMessage = Chat.of(roomId, userId, text);
        chatRepository.save(chatMessage);
        return chatMessage;
    }

    //채팅방의 채팅 가져오기
    @Transactional
    public Page<ChatDto> findChatsWithRoomId(Long chatRoomId, Pageable pageable){
        return chatRepository.findByRoomId(chatRoomId, pageable).map(ChatDto::from);
    }
}
