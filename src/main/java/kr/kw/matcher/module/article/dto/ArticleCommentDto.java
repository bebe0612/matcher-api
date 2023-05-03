package kr.kw.matcher.module.article.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentDto {
    Long id;
    Long articleId;
    Long userId;
    String content;
    LocalDateTime createdDt;

    public static ArticleCommentDto of(Long articleId, Long userId, String content) {
        return ArticleCommentDto.builder()
                .id(null)
                .articleId(articleId)
                .userId(userId)
                .content(content)
                .createdDt(null)
                .build();
    }

    public static ArticleCommentDto of(Long id, Long articleId, Long userId, String content, LocalDateTime createdDt) {
        return ArticleCommentDto.builder()
                .id(id)
                .articleId(articleId)
                .userId(userId)
                .content(content)
                .createdDt(createdDt)
                .build();
    }
}
