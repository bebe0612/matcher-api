package kr.kw.matcher.module.chat.chat_room_member.application.dto;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import kr.kw.matcher.module.chat.chat_room_member.domain.QChatRoomMember;
import kr.kw.matcher.module.user.domain.QUser;
import kr.kw.matcher.module.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class ChatRoomMemberDto {

    private Long id;
    private Long roomId;
    private User user;

    private LocalDateTime createdDt;

    public static QBean<ChatRoomMemberDto> fromBean(QChatRoomMember chatRoomMember, QUser user) {
        return Projections.bean(ChatRoomMemberDto.class,
                chatRoomMember.id,
                chatRoomMember.roomId,
                user,
                chatRoomMember.createdDt);
    }
}
