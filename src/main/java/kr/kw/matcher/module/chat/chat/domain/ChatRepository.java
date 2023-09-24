package kr.kw.matcher.module.chat.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<List<Chat>> findByRoomId(Long roomId);
}
