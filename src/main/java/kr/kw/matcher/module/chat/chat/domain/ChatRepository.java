package kr.kw.matcher.module.chat.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoomId(Long roomId);
}
