package kr.kw.matcher.module.article.controller;

import kr.kw.matcher.module.article.dto.request.ArticleCommentRequest;
import kr.kw.matcher.module.article.service.ArticleCommentService;
import kr.kw.matcher.module.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/v1/comments")
@RestController
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    // 댓글 추가
    @PostMapping
    public void postNewArticleComment(
            @Valid @RequestBody ArticleCommentRequest articleCommentRequest,
            @AuthenticationPrincipal Member member
    ) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(member.getId()));
    }

    // 댓글 업데이트
    @PatchMapping("/{commentId}")
    public void updateArticleComment(
            @PathVariable Long commentId,
            @Valid @RequestBody ArticleCommentRequest articleCommentRequest,
            @AuthenticationPrincipal Member member
    ) {
        articleCommentService.updateArticleComment(commentId, articleCommentRequest.toDto(member.getId()));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void deleteArticleComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal Member member
    ) {
        articleCommentService.deleteArticleComment(commentId, member.getId());
    }
}
