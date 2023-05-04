package kr.kw.matcher.module.article.repository;

import kr.kw.matcher.module.article.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    List<ArticleComment> findByArticle_Id(Long articleId);

    void deleteByIdAndUser_Id(Long articleCommentId, Long userId);
}
