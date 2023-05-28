package kr.kw.matcher.module.chat.chat.presentation;

import kr.kw.matcher.module.chat.chat.application.ChatService;
import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.MessageType;
import kr.kw.matcher.module.chat.chat.dto.ChatDto;
import kr.kw.matcher.module.chat.chat_room.application.ChatRoomService;
import kr.kw.matcher.module.chat.chat_room.domain.ChatRoom;
import kr.kw.matcher.module.chat.chat_room_member.application.dto.ChatRoomMemberDto;
import kr.kw.matcher.module.chat.redis.RedisPublisher;
import kr.kw.matcher.module.chat.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    @MessageMapping("/chat/message")  //"/pub/chat/message"
    public void message(ChatDto message, ChatRoomMemberDto chatRoomMember){
        if (MessageType.ENTER.equals(message.getType())) {
            chatRoomService.joinRoom(message.getRoomId(), message.getUserId());
            message.setText(message.getUserId() + "님이 입장하셨습니다.");
        }else if(MessageType.QUIT.equals(message.getType())){
            message.setText(message.getUserId()  + "님이 퇴장하셨습니다.");
            chatRoomService.leaveRoom(message.getRoomId(), message.getUserId());
        }
        chatService.createOne(message.getRoomId(),message.getUserId(), message.getText());
        redisPublisher.publish(chatRoomService.getTopic(message.getRoomId()), message);
    }
}
