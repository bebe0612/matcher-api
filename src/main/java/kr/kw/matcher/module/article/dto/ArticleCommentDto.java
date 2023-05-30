package kr.kw.matcher.module.article.dto;

import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.article.domain.ArticleComment;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.application.dto.UserDto;
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
    UserDto userDto;
    String content;
    LocalDateTime createdDt;

    public static ArticleCommentDto of(Long articleId, UserDto userDto, String content) {
        return ArticleCommentDto.builder()
                .id(null)
                .articleId(articleId)
                .userDto(userDto)
                .content(content)
                .createdDt(null)
                .build();
    }

    public static ArticleCommentDto of(Long id, Long articleId, UserDto userDto, String content, LocalDateTime createdDt) {
        return ArticleCommentDto.builder()
                .id(id)
                .articleId(articleId)
                .userDto(userDto)
                .content(content)
                .createdDt(createdDt)
                .build();
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return ArticleCommentDto.builder()
                .id(entity.getId())
                .articleId(entity.getArticle().getId())
                .userDto(UserDto.from(entity.getUser()))
                .content(entity.getContent())
                .createdDt(entity.getCreatedDt())
                .build();
    }

    public ArticleComment toEntity(Article article, User user) {
        return ArticleComment.of(
                article,
                user,
                content
        );
    }
}
