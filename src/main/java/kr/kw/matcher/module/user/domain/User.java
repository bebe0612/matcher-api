package kr.kw.matcher.module.user.domain;

import kr.kw.matcher.module.friend.domain.Friend;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private Long yearOfAdmission;

    @ToString.Exclude
    @OrderBy("createdDt DESC")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final Set<Friend> Friends = new LinkedHashSet<>(); // 친구

    @Column()
    private String description;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDt;

    @Column(nullable = false)
    private Boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static User of(Long id, String email, String password, String nickname, String schoolName, Long yearOfAdmission) {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .schoolName(schoolName)
                .yearOfAdmission(yearOfAdmission)
                .createdDt(LocalDateTime.now())
                .deleted(false)
                .build();
    }
}
