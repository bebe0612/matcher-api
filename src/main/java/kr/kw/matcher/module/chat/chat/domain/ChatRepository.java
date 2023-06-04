package kr.kw.matcher.module.chat.chat.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Page<Chat> findByRoomId(Long roomId, Pageable pageable);
}
