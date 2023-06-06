package kr.kw.matcher.module.article.dto.request;

import kr.kw.matcher.module.article.dto.ArticleCommentDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleCommentRequest {
    @NotNull Long articleId;
    @NotNull String content;

    public ArticleCommentDto toDto(Long userId) {
        return ArticleCommentDto.of(articleId, userId, content);
    }
}
