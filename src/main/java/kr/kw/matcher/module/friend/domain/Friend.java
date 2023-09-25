package kr.kw.matcher.module.friend.domain;

import kr.kw.matcher.module.user.domain.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend")
public class Friend {
    @Id
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column()
    private String description;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friend)) return false;
        Friend friend = (Friend) o;
        return id != null && id.equals(friend.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Friend of(User user, User alumni) { // user 의 친구로 alumni 추가
        return Friend.builder()
                .user(user)
                .id(alumni.getId())
                .email(alumni.getEmail())
                .nickname(alumni.getNickname())
                .createdDt(LocalDateTime.now())
                .build();
    }
}
