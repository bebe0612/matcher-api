package kr.kw.matcher.module.article.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    Long id;
    String title;
    String content;
    Long userId;
    LocalDateTime createdDt;

    public static ArticleDto of(String title, String content, Long userId) {
        return ArticleDto.builder()
                .id(null)
                .title(title)
                .content(content)
                .userId(userId)
                .createdDt(null)
                .build();
    }

    public static ArticleDto of(Long id, String title, String content, Long userId, LocalDateTime createdDt) {
        return ArticleDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .userId(userId)
                .createdDt(createdDt)
                .build();
    }
}
