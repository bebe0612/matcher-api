package kr.kw.matcher.module.article.dto.response;

import kr.kw.matcher.module.article.constant.SearchType;
import kr.kw.matcher.module.article.dto.ArticleDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlesResponse {
    Page<ArticleDto> articles;
    List<Integer> barNumbers;
    SearchType[] searchTypes;

    public static ArticlesResponse of(Page<ArticleDto> articles, List<Integer> barNumbers, SearchType[] searchTypes) {
        return ArticlesResponse.builder()
                .articles(articles)
                .barNumbers(barNumbers)
                .searchTypes(searchTypes)
                .build();
    }
}
