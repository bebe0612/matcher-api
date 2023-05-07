package kr.kw.matcher.module.article.dto;

import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.user.dto.UserDto;
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
    UserDto userDto;
    Set<ArticleCommentDto> articleCommentDtos;
    LocalDateTime createdDt;

    public static ArticleWithCommentsDto of(
            Long id,
            String title,
            String content,
            UserDto userDto,
            Set<ArticleCommentDto> articleCommentDtos,
            LocalDateTime createdDt
    ) {
        return ArticleWithCommentsDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .userDto(userDto)
                .articleCommentDtos(articleCommentDtos)
                .createdDt(createdDt)
                .build();
    }

    public static ArticleWithCommentsDto from(Article entity) {
        return ArticleWithCommentsDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                UserDto.from(entity.getUser()),
                entity.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getCreatedDt()
        );
    }
}
