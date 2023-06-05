package kr.kw.matcher.module.user.application.dto;

import kr.kw.matcher.module.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    Long id;
    String email;
    String nickname;
    String schoolName;
    Long yearOfAdmission;
    LocalDateTime createdDt;

    public static UserDto of(Long id) {
        return UserDto.builder()
                .id(id)
                .build();
    }

    public static UserDto from(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .build();
    }

    public User toEntity() {
        return User.of(
                id,
                email,
                null,
                nickname,
                schoolName,
                yearOfAdmission
        );
    }
}
