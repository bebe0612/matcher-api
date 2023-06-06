package kr.kw.matcher.module.article.service;

import kr.kw.matcher.module.article.constant.SearchType;
import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.article.dto.ArticleDto;
import kr.kw.matcher.module.article.dto.ArticleWithCommentsDto;
import kr.kw.matcher.module.article.repository.ArticleRepository;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import kr.kw.matcher.module.user.application.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@Slf4j
@DisplayName("비즈니스 로직 - 게시판")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks private ArticleService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private UserRepository userRepository;

    @DisplayName("검색어 없이 게시글을 검색하면, 게시판 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // Given
        User user = createUser();
        Pageable pageable = Pageable.ofSize(20);
        given(userRepository.getReferenceById(user.getId())).willReturn(user); // searchArticles 메소드에서 userRepository.getReferenceById 을 호출하기 때문에 포함해야 함
        given(articleRepository.findByUser_SchoolNameAndUser_YearOfAdmission(
                user.getSchoolName(),
                user.getYearOfAdmission(),
                pageable
        )).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(user.getId(), null, null, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(userRepository).should().getReferenceById(user.getId());
        then(articleRepository)
                .should()
                .findByUser_SchoolNameAndUser_YearOfAdmission(
                        user.getSchoolName(),
                        user.getYearOfAdmission(),
                        pageable
                );
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시판 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // Given
        User user = createUser();
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(userRepository.getReferenceById(user.getId())).willReturn(user);
        given(articleRepository.findByTitleContainingAndUser_SchoolNameAndUser_YearOfAdmission(
                searchKeyword,
                user.getSchoolName(),
                user.getYearOfAdmission(),
                pageable
        )).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(
                user.getId(),
                searchType,
                searchKeyword,
                pageable
        );

        // Then
        assertThat(articles).isEmpty();
        then(userRepository).should().getReferenceById(user.getId());
        then(articleRepository)
                .should()
                .findByTitleContainingAndUser_SchoolNameAndUser_YearOfAdmission(
                        searchKeyword,
                        user.getSchoolName(),
                        user.getYearOfAdmission(),
                        pageable
                );
    }

    @DisplayName("게시글 ID로 조회하면, 댓글 달긴 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleWithComments_thenReturnsArticleWithComments() {
        // Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        // When
        ArticleWithCommentsDto dto = sut.getArticleWithComments(articleId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글이 없으면, 예외를 던진다.")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() {
        // Given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getArticleWithComments(articleId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글 조회를 실패했습니다. 게시글을 찾을 수 없습니다. - articleId : " + articleId);
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // Given
        ArticleDto dto = createArticleDto();
        given(userRepository.getReferenceById(dto.getId())).willReturn(createUser());
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());

        // When
        sut.saveArticle(dto);

        // Then
        then(userRepository).should().getReferenceById(dto.getId());
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        // Given
        Article article = createArticle();
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", createUserDto());
        given(articleRepository.getReferenceById(article.getId())).willReturn(article);
        given(userRepository.getReferenceById(dto.getId())).willReturn(createUser());

        // When
        sut.updateArticle(article.getId(), dto);

        // Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.getTitle())
                .hasFieldOrPropertyWithValue("content", dto.getContent());
        then(articleRepository).should().getReferenceById(dto.getId());
        then(userRepository).should().getReferenceById(dto.getId());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
    @Test
    void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() {
        // Given
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", UserDto.of(1L));
        given(articleRepository.getReferenceById(dto.getId())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticle(dto.getId(), dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.getId());
    }

    @DisplayName("게시글의 ID 와 유저 ID 를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // Given
        Long articleId = 1L;
        Long userId = 1L;
        willDoNothing().given(articleRepository).deleteByIdAndUser_Id(articleId, userId);

        // When
        sut.deleteArticle(1L, 1L);

        // Then
        then(articleRepository).should().deleteByIdAndUser_Id(articleId, userId);
    }

    @DisplayName("게시글 수를 조회하면, 게시글 수를 반환한다")
    @Test
    void givenNothing_whenCountingArticles_thenReturnsArticleCount() {
        // Given
        long expected = 0L;
        given(articleRepository.count()).willReturn(expected);

        // When
        long actual = sut.getArticleCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(articleRepository).should().count();
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

    private Article createArticle() {
        Article article = Article.of(
                "title",
                "content",
                createUser()
        );
        ReflectionTestUtils.setField(article, "id", 1L);

        return article;
    }

    private UserDto createUserDto() {
        return UserDto.of(1L);
    }

    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content", createUserDto());
    }

    private ArticleDto createArticleDto(String title, String content, UserDto userDto) {
        return ArticleDto.of(1L, title, content, userDto.getId(), userDto.getNickname(), LocalDateTime.now());
    }
}
