package kr.kw.matcher.module.chat.chat_room_domain.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room_domain",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"domain_type", "domain_id", "user_id"})
        })
public class ChatRoomDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "domain_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DomainType domainType;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "domain_id", nullable = false)
    private Long domainId;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private LocalDateTime createdDt;

    public static ChatRoomDomain of(Long userId, DomainType domainType, Long domainId, Long roomId) {
        return ChatRoomDomain.builder()
                .userId(userId)
                .domainType(domainType)
                .domainId(domainId)
                .roomId(roomId)
                .createdDt(LocalDateTime.now())
                .build();
    }
}
