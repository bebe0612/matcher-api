package kr.kw.matcher.module.article.repository;

import kr.kw.matcher.module.article.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
