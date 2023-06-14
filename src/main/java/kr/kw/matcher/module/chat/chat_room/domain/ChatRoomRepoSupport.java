package kr.kw.matcher.module.chat.chat_room.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.kw.matcher.module.chat.chat_room.application.UserChatRoomDetailDto;
import kr.kw.matcher.module.chat.chat_room.application.dto.UserChatRoomBriefDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static kr.kw.matcher.module.chat.chat_room_member.domain.QChatRoomMember.chatRoomMember;
import static kr.kw.matcher.module.chat.chat.domain.QChat.chat;
import static kr.kw.matcher.module.chat.chat_room.domain.QChatRoom.chatRoom;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ChatRoomRepoSupport {
    private final JPAQueryFactory queryFactory;

    public UserChatRoomDetailDto getUserChatRoom(Long userId, Long roomId) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(chatRoom.id.eq(roomId));

        return queryFactory.select(UserChatRoomDetailDto.getBean(chatRoom, chatRoomDomain, chatRoomMember))
                .from(chatRoom)
                .where(where)
                .innerJoin(chatRoomDomain).on(chatRoomDomain.roomId.eq(chatRoom.id))
                .innerJoin(chatRoomMember).on(chatRoomMember.userId.eq(userId).and(chatRoomMember.roomId.eq(roomId)))
                .fetchOne();
    }
    public List<UserChatRoomBriefDto> findAllUserChatRooms(Long userId, DomainType domainType) {

        BooleanBuilder where = new BooleanBuilder();

        where.and(chatRoomMember.userId.eq(userId));
        QChat subChat = new QChat("subChat");

        return queryFactory.select(
                        UserChatRoomBriefDto.getBean(chatRoomDomain, chatRoom, chat, chatRoomMember))
                .from(chatRoomMember)
                .where(where)
                .innerJoin(chatRoom).on(chatRoom.id.eq(chatRoomMember.roomId).and(chatRoom.status.eq(ChatRoomStatus.ACTIVE)))
                .innerJoin(chatRoomDomain).on(chatRoomDomain.roomId.eq(chatRoom.id).and(chatRoomDomain.domainType.eq(domainType)))
                .innerJoin(chat).on(chat.roomId.eq(chatRoomMember.roomId))
                .innerJoin(club).on(club.id.eq(chatRoomDomain.domainId))
                .innerJoin(user).on(user.id.eq(club.presidentId))
                .where(chat.seqId.eq(
                        JPAExpressions
                                .select(subChat.seqId.max())
                                .from(subChat)
                                .where(subChat.roomId.eq(chatRoom.id))
                ))
                .orderBy(chatRoomMember.createdDt.desc())
                .fetch();
    }
}
