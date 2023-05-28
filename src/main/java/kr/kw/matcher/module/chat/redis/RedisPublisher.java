package kr.kw.matcher.module.chat.redis;

import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatDto message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
