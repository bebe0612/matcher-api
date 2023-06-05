package kr.kw.matcher.module.article.repository;

import com.querydsl.core.types.dsl.StringExpression;
import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.article.domain.QArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // 모든 필드에 대한 검색 기능 추가
        QuerydslBinderCustomizer<QArticle> // 추가적인 검색 기능을 위해 추가
{
    Page<Article> findByUser_SchoolNameAndUser_YearOfAdmission(
            String schoolName,
            Long yearOfAdmission,
            Pageable pageable
    );
    Page<Article> findByTitleContainingAndUser_SchoolNameAndUser_YearOfAdmission(
            String title,
            String schoolName,
            Long yearOfAdmission,
            Pageable pageable
    );
    Page<Article> findByContentContainingAndUser_SchoolNameAndUser_YearOfAdmission(
            String content,
            String schoolName,
            Long yearOfAdmission,
            Pageable pageable
    );
    Page<Article> findByUser_NicknameContainingAndUser_SchoolNameAndUser_YearOfAdmission(
            String userNickname,
            String schoolName,
            Long yearOfAdmission,
            Pageable pageable
    );

    void deleteByIdAndUser_Id(Long articleId, Long userId);

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content);

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 부분 검색 가능, 대소문자 구분 X
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
    }
}
