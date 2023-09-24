package kr.kw.matcher.module.chat.chat.application;

import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat_room.repository.RedisChatRoomRepository;
import kr.kw.matcher.module.chat.redis.RedisPublisher;
import kr.kw.matcher.module.chat.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final RedisPublisher redisPublisher;
    private final RedisSubscriber redisSubscriber;
    private final RedisChatRoomRepository redisChatRoomRepository;
    private final RedisMessageListenerContainer redisMessageListener;

    public void sendMessage(Chat chat){
        ChannelTopic topic = redisChatRoomRepository.getTopic(chat.getRoomId().toString());
        if(topic == null){
            topic = new ChannelTopic(chat.getRoomId().toString());
            redisMessageListener.addMessageListener(redisSubscriber, topic);
        }
        redisPublisher.publish(topic, chat);
    }
}
