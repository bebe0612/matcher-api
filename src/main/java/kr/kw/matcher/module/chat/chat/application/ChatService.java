package kr.kw.matcher.module.chat.chat.application;

import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.ChatRepository;
import kr.kw.matcher.module.chat.chat.presentation.request.ChatCreateBody;
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
    private final ChatRepository chatRepository;

    public void sendMessage(ChatCreateBody body) {
        ChannelTopic topic = redisChatRoomRepository.getTopic(body.getRoomId().toString());

        if(topic == null){
            topic = new ChannelTopic(body.getRoomId().toString());
            redisMessageListener.addMessageListener(redisSubscriber, topic);
        }

        Chat chat = Chat.of(body.getRoomId(), body.getUserId(), body.getText());
        chat = chatRepository.save(chat);

        redisPublisher.publish(topic, chat);
    }
}
