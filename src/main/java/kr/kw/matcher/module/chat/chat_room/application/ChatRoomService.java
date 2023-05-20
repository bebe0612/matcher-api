package kr.kw.matcher.module.chat.chat_room.application;

import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoomRepository;
import kr.kw.matcher.module.chat.chat_room.dto.ChatRoomDto;
import kr.kw.matcher.module.chat.chat_room_member.application.ChatRoomMemberService;
import kr.kw.matcher.module.chat.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
    private static final String CHAT_ROOM = "CHAT_ROOM";
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMemberService chatRoomMemberService;

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, ChatRoom> opsHashChatRoom; //chatRoom => "CHAT_ROOMS", {roomId}, chatRoom

    private Map<Long, ChannelTopic> topics;

    @PostConstruct
    public void init(){
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }
    @Transactional
    public ChatRoom createOne(Long userId) {
        ChatRoom willSavedChatRoom = ChatRoom.of(userId, "");
        willSavedChatRoom.setMemberCount(1L);

        ChatRoom chatRoom = chatRoomRepository.save(willSavedChatRoom);
        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getId(), chatRoom);
        ChannelTopic topic = new ChannelTopic(Long.toString(chatRoom.getId()));
        redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
        topics.put(chatRoom.getId(), topic);

        // host user join
        chatRoomMemberService.createOne(chatRoom.getId(), userId);
        return chatRoom;
    }

    @Transactional
    public void joinRoom(Long roomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();
        ChannelTopic topic = topics.get(roomId);

        chatRoom.setMemberCount(chatRoom.getMemberCount() + 1L);
        chatRoomMemberService.createOne(roomId, userId);
    }

    @Transactional
    public List<ChatRoomDto> findAllRoom(){
        return opsHashChatRoom.values(CHAT_ROOM).stream().map(i -> ChatRoomDto.from(i)).collect(Collectors.toList());
    }

    @Transactional
    public ChatRoomDto findRoomById(Long roomId){
        //ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();
        ChatRoom chatRoom = opsHashChatRoom.get(CHAT_ROOM,  roomId);
        ChatRoomDto dto  = ChatRoomDto.from(chatRoom);
        return dto;
    }

    @Transactional
    public ChannelTopic getTopic(Long roomId) {
        return topics.get(roomId);
    }
}
