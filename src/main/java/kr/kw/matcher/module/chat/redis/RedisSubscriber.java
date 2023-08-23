package kr.kw.matcher.module.chat.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kw.matcher.module.chat.chat.domain.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageSendingOperations;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            Chat chat = objectMapper.readValue(publishMessage, Chat.class);
            messageSendingOperations.convertAndSend("/sub/chat/room/"+chat.getRoomId(), chat);
        } catch(Exception e){
            log.info("errorrrrrrrrrrrrrrrrrrrrrrrrrrr");
        }
    }
}
