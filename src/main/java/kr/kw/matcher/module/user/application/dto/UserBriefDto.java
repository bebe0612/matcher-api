package kr.kw.matcher.module.user.application.dto;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import kr.kw.matcher.module.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserBriefDto implements Serializable {
    private Long id;
    private String nickname;

    public static UserBriefDto of(User user) {
        UserBriefDto dto = new UserBriefDto();
        dto.setId(user.getId());
        dto.setNickname(user.getNickname());
        return dto;
    }

    public static QBean<UserBriefDto> getBean(QUser user) {
        return Projections.bean(
                UserBriefDto.class,
                user.id,
                user.nickname
        );
    }
}