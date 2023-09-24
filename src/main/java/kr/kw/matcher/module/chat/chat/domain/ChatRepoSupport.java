package kr.kw.matcher.module.chat.chat.domain;


import static kr.kw.matcher.module.user.domain.QUser.user;
import static kr.kw.matcher.module.chat.chat.domain.QChat.chat;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.kw.matcher.module.chat.chat.application.dto.ChatDto;
import kr.kw.matcher.module.chat.chat.presentation.GetChatParams;
import kr.kw.matcher.module.chat.chat.presentation.request.ChatGetParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ChatRepoSupport {
    private final JPAQueryFactory queryFactory;
    public List<ChatDto> getChats(Long roomId, ChatGetParams params, LocalDateTime afterDt) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(chat.roomId.eq(roomId));
        where.and(chat.createdDt.goe(afterDt));

        if(params.getGt() != null) {
            if(params.getGt()) {
                where.and(chat.seqId.gt(params.getOffsetSeqId()));
            } else {
                where.and(chat.seqId.lt(params.getOffsetSeqId()));
            }
        }

        return queryFactory
                .select(
                        Projections.bean(ChatDto.class,
                                chat.id,
                                chat.userId,
                                UserBriefDto.getBean(user).as("user"),
                                chat.roomId,
                                chat.seqId,
                                chat.createdDt,
                                chat.chatType,
                                chat.text,
                                chat.imageUrl,
                                chat.imageThumbUrl,
                                chat.locationX,
                                chat.locationY
                        ))
                .from(chat)
                .where(where)
                .leftJoin(user).on(user.id.eq(chat.userId))
                .limit(params.getLimit())
                .orderBy(chat.seqId.desc())
                .fetch();
    }
    public List<Chat> getChatBetweenSeq(Long roomId, GetChatParams filter){
        BooleanBuilder where = new BooleanBuilder();

        where.and(chat.roomId.eq(roomId));

        where.and(chat.seqId.between(filter.getStartSeqId(), filter.getLastSeqId()));

        return queryFactory
                .selectFrom(chat)
                .where(where)
                .orderBy(chat.seqId.asc())
                .limit(filter.getLimit())
                .fetch();
    }

    public Long getLastChatSeqId(Long roomId) {

        BooleanBuilder where = new BooleanBuilder();

        where.and(chat.roomId.eq(roomId));

        return queryFactory
                .select(chat.seqId)
                .from(chat)
                .where(where)
                .orderBy(chat.seqId.desc())
                .limit(1)
                .fetchOne();

    }
}
