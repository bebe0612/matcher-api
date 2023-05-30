package kr.kw.matcher.module.article.repository;

import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.article.domain.ArticleComment;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired ArticleRepository articleRepository;
    @Autowired ArticleCommentRepository articleCommentRepository;
    @Autowired UserRepository userRepository;

    @DisplayName("select + insert 테스트")
    @Test
    void test_select_and_insert() {
        // When
        User savedUser = userRepository.save(User.of(1L, "email", "password", "name"));
        Article savedArticle = articleRepository.save(Article.of("new", "new article", savedUser));
        ArticleComment savedArticleComment1 = articleCommentRepository.save(ArticleComment.of(savedArticle, savedUser, "new comment1"));
        ArticleComment savedArticleComment2 = articleCommentRepository.save(ArticleComment.of(savedArticle, savedUser, "new comment2"));

        Article test = articleRepository.findById(savedArticle.getId()).orElseThrow();

        // Then
        assertThat(articleRepository.count()).isEqualTo(1);
        assertThat(articleCommentRepository.count()).isEqualTo(2);
    }

    @DisplayName("update 테스트")
    @Test
    void test_update() {
        // Given
        User savedUser = userRepository.save(User.of(1L, "email", "password", "name"));
        Article savedArticle = articleRepository.save(Article.of("new", "new article", savedUser));

        // When
        savedArticle.setTitle("new new");

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("title", "new new");
    }

    @DisplayName("delete 테스트")
    @Test
    void test_delete() {
        // When
        User savedUser = userRepository.save(User.of(1L, "email", "password", "name"));
        Article savedArticle = articleRepository.save(Article.of("new", "new article", savedUser));

        // When
        articleRepository.delete(savedArticle);

        // Then
        assertThat(articleRepository.count()).isEqualTo(0);
    }
}