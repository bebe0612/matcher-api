package kr.kw.matcher.module.chat.chat_room.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByHostIdAndFriendId(Long hostId, Long friendId);
}
