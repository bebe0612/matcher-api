package kr.kw.matcher.module.article.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleWithCommentsDto {
    Long id;
    String title;
    String content;
    Long userId;
    Set<ArticleCommentDto> articleCommentDtos;
    LocalDateTime createdDt;

    public static ArticleWithCommentsDto of(
            Long id,
            String title,
            String content,
            Long userId,
            Set<ArticleCommentDto> articleCommentDtos,
            LocalDateTime createdDt
    ) {
        return ArticleWithCommentsDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .userId(userId)
                .articleCommentDtos(articleCommentDtos)
                .createdDt(createdDt)
                .build();
    }
}
