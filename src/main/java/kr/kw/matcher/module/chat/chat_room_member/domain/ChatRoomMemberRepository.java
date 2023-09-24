package kr.kw.matcher.module.chat.chat_room_member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    public Optional<ChatRoomMember> findByRoomIdAndUserId(Long roomId, Long userId);
    Optional<List<ChatRoomMember>> deleteByRoomIdAndUserId(Long roomId, Long userId);
}
