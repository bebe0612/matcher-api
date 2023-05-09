package kr.kw.matcher.module.chat.chat_room_member.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.kw.matcher.module.chat.chat_room_member.application.dto.ChatRoomMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.kw.matcher.module.chat.chat_room_member.domain.QChatRoomMember.chatRoomMember;
import static kr.kw.matcher.module.user.domain.QUser.user;


@RequiredArgsConstructor
@Repository
@Slf4j
public class ChatRoomMemberRepoSupport {
    private final JPAQueryFactory queryFactory;

    public List<ChatRoomMemberDto> findRoomMembersByRoomId(Long roomId) {
        BooleanBuilder where = new BooleanBuilder();

        where.and(chatRoomMember.roomId.eq(roomId));
        where.and(chatRoomMember.deleted.ne(true));

        return queryFactory
                .select(ChatRoomMemberDto.fromBean(chatRoomMember, user))
                .from(chatRoomMember)
                .innerJoin(user).on(user.id.eq(chatRoomMember.userId))
                .where(where)
                .fetch();
    }
}
