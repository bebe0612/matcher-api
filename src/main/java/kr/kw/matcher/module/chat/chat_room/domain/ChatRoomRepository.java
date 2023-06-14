package kr.kw.matcher.module.chat.chat_room.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "update chat_room set member_count = member_count - 1 where id=:id", nativeQuery = true)
    void minusMemberCount(@io.lettuce.core.dynamic.annotation.Param("id") Long id);
}
