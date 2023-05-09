package kr.kw.matcher.module.chat.chat_room_member.presentation;

import kr.kw.matcher.module.chat.chat_room_member.application.ChatRoomMemberService;
import kr.kw.matcher.module.chat.chat_room_member.application.dto.ChatRoomMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ChatRoomMemberController {
    private final ChatRoomMemberService chatRoomMemberService;

    @GetMapping("/chats/{roomId}/members")
    public List<ChatRoomMemberDto> getChatRoomMembers(@PathVariable Long roomId) {
        return chatRoomMemberService.getChatRoomMembers(roomId);
    }
}
