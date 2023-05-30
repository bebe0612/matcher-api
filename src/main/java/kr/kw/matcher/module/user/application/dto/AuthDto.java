package kr.kw.matcher.module.user.application.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String token;
    private Long userId;
}
