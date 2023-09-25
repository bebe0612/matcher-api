package kr.kw.matcher.module.friend.dto;

import kr.kw.matcher.module.friend.domain.Friend;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {
    Long id;
    String email;
    String nickname;

    public static FriendDto from(Friend entity) {
        return FriendDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .build();
    }
}
