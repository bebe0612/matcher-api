package kr.kw.matcher.module.chat.chat_room.repository;

import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.redis.RedisPublisher;
import kr.kw.matcher.module.chat.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RedisChatRoomRepository {
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisPublisher redisPublisher;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate redisTemplate;
    private final RedisMessageListenerContainer redisMessageListener;
    private Map<String, ChannelTopic> topics;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;

    @PostConstruct
    private void init(){
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        return opsHashChatRoom.values(CHAT_ROOMS);
    }
    public ChatRoom findRoomById(String id) { return opsHashChatRoom.get(CHAT_ROOMS, id); }

    public ChatRoom save(ChatRoom chatRoom) {
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getId().toString(), chatRoom);
        return chatRoom;
    }
    public void enterChatRoom(String roomId) {

        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }
    public ChannelTopic getTopic(String roomId){
        return topics.get(roomId);
    }
}
