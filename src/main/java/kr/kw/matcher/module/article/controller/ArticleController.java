package kr.kw.matcher.module.article.controller;

import kr.kw.matcher.module.article.constant.SearchType;
import kr.kw.matcher.module.article.dto.ArticleDto;
import kr.kw.matcher.module.article.dto.ArticleWithCommentsDto;
import kr.kw.matcher.module.article.dto.request.ArticleRequest;
import kr.kw.matcher.module.article.dto.response.ArticlesResponse;
import kr.kw.matcher.module.article.service.ArticleService;
import kr.kw.matcher.module.article.service.PaginationService;
import kr.kw.matcher.module.member.domain.Member;
import kr.kw.matcher.module.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/board")
@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final PaginationService paginationService;
    private final UserService userService;

    // 게시판 페이지
    @GetMapping
    public ArticlesResponse articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchKeyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Member member
    ) {
        Page<ArticleDto> articles = articleService.searchArticles(member.getId(), searchType, searchKeyword, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        return ArticlesResponse.of(
                articles,
                barNumbers,
                SearchType.values()
        );
    }

    // 게시글 상세 페이지
    @GetMapping("/{articleId}")
    public ArticleWithCommentsDto article(@PathVariable Long articleId) {
        ArticleWithCommentsDto article = articleService.getArticleWithComments(articleId);
        article.setArticleCount(articleService.getArticleCount());

        return article;
    }

    // 게시글 입력
    @PostMapping
    public void postNewArticle(
            @Valid @RequestBody ArticleRequest articleRequest,
            @AuthenticationPrincipal Member member
    ) {
        String nickname = articleService.getUserNickname(member.getId());
        articleService.saveArticle(articleRequest.toDto(member.getId(), nickname));
    }

    // 게시글 업데이트
    @PatchMapping("/{articleId}")
    public void updateArticle(
            @PathVariable Long articleId,
            @Valid @RequestBody ArticleRequest articleRequest,
            @AuthenticationPrincipal Member member
    ) {
        String nickname = articleService.getUserNickname(member.getId());
        articleService.updateArticle(articleId, articleRequest.toDto(member.getId(), nickname));
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}")
    public void deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal Member member
    ) {
        articleService.deleteArticle(articleId, member.getId());
    }
}
