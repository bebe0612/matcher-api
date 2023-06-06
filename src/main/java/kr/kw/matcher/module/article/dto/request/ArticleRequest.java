package kr.kw.matcher.module.article.dto.request;

import kr.kw.matcher.module.article.dto.ArticleDto;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ArticleRequest {
    @NotNull String title;
    @NotNull String content;

    public ArticleDto toDto(Long userId, String nickname) {
        return ArticleDto.of(title, content, userId, nickname);
    }
}
