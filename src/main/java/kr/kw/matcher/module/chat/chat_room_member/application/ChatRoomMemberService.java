package kr.kw.matcher.module.chat.chat_room_member.application;

import kr.kw.matcher.module.chat.chat_room_member.application.dto.ChatRoomMemberDto;
import kr.kw.matcher.module.chat.chat_room_member.domain.ChatRoomMember;
import kr.kw.matcher.module.chat.chat_room_member.domain.ChatRoomMemberRepoSupport;
import kr.kw.matcher.module.chat.chat_room_member.domain.ChatRoomMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomMemberService {
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomMemberRepoSupport chatRoomMemberRepoSupport;

    @Transactional
    public ChatRoomMember createOne(Long roomId, Long userId) {
        chatRoomMemberRepository.findByRoomIdAndUserId(roomId, userId).ifPresent(chatRoomMember -> {
            throw new RuntimeException("이미 존재하는 채팅방 멤버입니다.");
        });

        ChatRoomMember chatroomMember = ChatRoomMember.of(roomId, userId);

        return chatRoomMemberRepository.save(chatroomMember);
    }
    @Transactional
    public void deleteOne(Long roomId, Long userId){
        chatRoomMemberRepository.findByRoomIdAndUserId(roomId, userId)
                .ifPresentOrElse(
                        (value)->{ chatRoomMemberRepository.deleteByRoomIdAndUserId(roomId, userId); },
                        ()->{throw new RuntimeException("사용자 " + userId.toString() + "는 채팅방 " + roomId.toString()  + "에 존재하지 않습니다."); }
                );
    }


    public List<ChatRoomMemberDto> getChatRoomMembers(Long roomId) {
        return chatRoomMemberRepoSupport.findRoomMembersByRoomId(roomId);
    }
}
