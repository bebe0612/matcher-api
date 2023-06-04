package kr.kw.matcher.module.chat.chat_room.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.kw.matcher.module.chat.chat_room_member.domain.QChatRoomMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ChatRoomRepoSupport {     //아직 미구현
    private final JPAQueryFactory queryFactory;
    QChatRoom chatRoom  = QChatRoom.chatRoom;
    QChatRoomMember chatRoomMember = QChatRoomMember.chatRoomMember;

}
