package kr.kw.matcher.module.article.service;

import kr.kw.matcher.core.exception.ConflictException;
import kr.kw.matcher.core.exception.NotFoundException;
import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.article.domain.ArticleComment;
import kr.kw.matcher.module.article.dto.ArticleCommentDto;
import kr.kw.matcher.module.article.repository.ArticleCommentRepository;
import kr.kw.matcher.module.article.repository.ArticleRepository;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import kr.kw.matcher.module.user.application.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {
    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleCommentRepository articleCommentRepository;
    @Mock
    private UserRepository userRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // Given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        // When
        List<ArticleCommentDto> actual = sut.searchArticleComments(articleId);

        // Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.findById(dto.getArticleId())).willReturn(Optional.of(createArticle()));
        given(userRepository.findById(dto.getUserId())).willReturn(Optional.of(createUser()));
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().findById(dto.getArticleId());
        then(userRepository).should().findById(dto.getUserId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 예외를 던진다.")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");

        // When // Then
        Assertions.assertThrows(ConflictException.class, () -> {
            sut.saveArticleComment(dto);
        });

        then(userRepository).shouldHaveNoInteractions();
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() { // TODO: 수정하기
        // Given
        String oldContent = "content";
        String updatedContent = "댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto dto = createArticleCommentDto(updatedContent);
        given(articleCommentRepository.findById(dto.getId())).willReturn(Optional.of(articleComment));

        // When
        sut.updateArticleComment(articleComment.getId(), dto);

        // Then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(articleCommentRepository).should().findById(dto.getId());
    }

    @DisplayName("없는 댓글 정보를 수정하려고 하면, 예외를 반환한다.")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() { // TODO: 수정하기
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleCommentRepository.findById(dto.getId())).willReturn(Optional.empty());

        // When
        Assertions.assertThrows(NotFoundException.class, () -> {
            sut.updateArticleComment(dto.getId(), dto);
        });

        // Then
        then(articleCommentRepository).should().findById(dto.getId());
    }

    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // Given
        Long articleCommentId = 1L;
        Long userId = 1L;
        willDoNothing().given(articleCommentRepository).deleteByIdAndUser_Id(articleCommentId, userId);

        // When
        sut.deleteArticleComment(articleCommentId, userId);

        // Then
        then(articleCommentRepository).should().deleteByIdAndUser_Id(articleCommentId, userId);
    }

    private User createUser() {
        return User.of(
                1L,
                "email",
                "password",
                "nickname",
                "kwangwoon",
                2019L
        );
    }

    private UserDto createUserDto() {
        return UserDto.of(1L);
    }

    private Article createArticle() {
        Article article = Article.of(
                "title",
                "content",
                createUser()
        );
        ReflectionTestUtils.setField(article, "id", 1L);

        return article;
    }

    private ArticleComment createArticleComment(String content) {
        ArticleComment articleComment = ArticleComment.of(
                Article.of("title", "content", createUser()),
                createUser(),
                content
        );
        ReflectionTestUtils.setField(articleComment, "id", 1L);

        return articleComment;
    }

    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                1L,
                1L,
                content,
                LocalDateTime.now()
        );
    }
}
