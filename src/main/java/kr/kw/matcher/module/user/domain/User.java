package kr.kw.matcher.module.user.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

    @Column()
    private String nickname;

    @Column()
    private String description;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDt;

    @Column(nullable = false)
    private Boolean deleted;

    public static User of(Long id) {
        return User.builder()
                .id(id)
                .createdDt(LocalDateTime.now())
                .deleted(false)
                .build();
    }
}
