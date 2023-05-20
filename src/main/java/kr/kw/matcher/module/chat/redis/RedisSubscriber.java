package kr.kw.matcher.module.chat.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageTemplate;

    @Override //발행된 메시지를 onMassage()가 처리함
    public void onMessage(Message message, byte[] pattern) {
        try{
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatDto chatMessage = objectMapper.readValue(publishMessage, ChatDto.class); //chatMessage로 매핑
            redisTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage); // /sub/chat/room/{roomId}를 구독하고 있는 유저에게 send
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
