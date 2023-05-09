package kr.kw.matcher.module.chat.chat_room.domain;

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
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hostId;

    @Column()
    private String name;

    @Column()
    private Long memberCount;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDt;

    @Column(nullable = false)
    private Boolean deleted;

    public static ChatRoom of(Long hostId, String name) {
        return ChatRoom.builder()
                .hostId(hostId)
                .name(name)
                .memberCount(0L)
                .createdDt(LocalDateTime.now())
                .deleted(false)
                .build();
    }
}
