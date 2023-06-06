package kr.kw.matcher.module.article.dto;

import kr.kw.matcher.module.article.domain.Article;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleWithCommentsDto {
    Long id;
    String title;
    String content;
    String nickname;
    Set<ArticleCommentDto> articleCommentDtos;
    LocalDateTime createdDt;
    Long articleCount;

    public static ArticleWithCommentsDto of(
            Long id,
            String title,
            String content,
            String nickname,
            Set<ArticleCommentDto> articleCommentDtos,
            LocalDateTime createdDt
    ) {
        return ArticleWithCommentsDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .nickname(nickname)
                .articleCommentDtos(articleCommentDtos)
                .createdDt(createdDt)
                .build();
    }

    public static ArticleWithCommentsDto from(Article entity) {
        return ArticleWithCommentsDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getUser().getNickname(),
                entity.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getCreatedDt()
        );
    }
}
