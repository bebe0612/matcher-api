package kr.kw.matcher.module.chat.chat_room.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select r from ChatRoomMember m, ChatRoom r where m.userId = :userId and m.roomId = r.id")
    public List<ChatRoom> findAllRoomByUserId(@Param("userId") Long userId);
}
