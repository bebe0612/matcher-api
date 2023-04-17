package kr.kw.matcher.module.chat.chat_room_member.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room_member")
public class ChatRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDt;

    @Column(nullable = false)
    private Boolean deleted;

    public static ChatRoomMember of(Long userId) {
        return ChatRoomMember.builder()
                .userId(userId)
                .createdDt(LocalDateTime.now())
                .deleted(false)
                .build();
    }
}
