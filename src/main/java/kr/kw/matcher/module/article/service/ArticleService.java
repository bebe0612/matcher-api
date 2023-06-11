package kr.kw.matcher.module.article.service;

import kr.kw.matcher.core.exception.ConflictException;
import kr.kw.matcher.core.exception.ForbiddenException;
import kr.kw.matcher.core.exception.NotFoundException;
import kr.kw.matcher.module.article.constant.SearchType;
import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.article.dto.ArticleDto;
import kr.kw.matcher.module.article.dto.ArticleWithCommentsDto;
import kr.kw.matcher.module.article.repository.ArticleRepository;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // SearchType 에 따른 게시글 조회
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(Long userId, SearchType searchType, String searchKeyword, Pageable pageable) {
        User user = userRepository.getReferenceById(userId);

        if (searchKeyword == null || searchKeyword.isBlank()) { // 키워드 없이 검색한다면 사용자 학교 전체 + 동일 졸업년도 게시글 조회
            return articleRepository.findByUser_SchoolNameAndUser_YearOfAdmission(
                    user.getSchoolName(),
                    user.getYearOfAdmission(),
                    pageable
            ).map(ArticleDto::from);
        }

        if (searchType == SearchType.TITLE) {
            return articleRepository.findByTitleContainingAndUser_SchoolNameAndUser_YearOfAdmission(
                    searchKeyword,
                    user.getSchoolName(),
                    user.getYearOfAdmission(),
                    pageable
            ).map(ArticleDto::from);
        }
        else if (searchType == SearchType.CONTENT) {
            return articleRepository.findByContentContainingAndUser_SchoolNameAndUser_YearOfAdmission(
                    searchKeyword,
                    user.getSchoolName(),
                    user.getYearOfAdmission(),
                    pageable
            ).map(ArticleDto::from);
        }
        else {
            return articleRepository.findByUser_NicknameContainingAndUser_SchoolNameAndUser_YearOfAdmission(
                    searchKeyword,
                    user.getSchoolName(),
                    user.getYearOfAdmission(),
                    pageable
            ).map(ArticleDto::from);
        }
    }

    // 특정 게시글 조회
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(NotFoundException::new);
    }

    // 게시글 저장
    public void saveArticle(ArticleDto dto) {
        ArticleDto save = ArticleDto.of(dto);
        User user = userRepository.findById(dto.getUserId()).orElseThrow(ConflictException::new);

        articleRepository.save(save.toEntity(user));
    }

    // 게시글 업데이트
    public void updateArticle(Long articleId, ArticleDto dto) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundException::new);

        if (!article.getUser().getId().equals(dto.getUserId())) {
            throw new ForbiddenException("게시글 작성자만 수정할 수 있습니다.");
        }

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
    }

    // 게시글 삭제
    // TODO: soft delete 로 변경 예정
    public void deleteArticle(Long articleId, Long userId) {
        articleRepository.deleteByIdAndUser_Id(articleId, userId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    public String getUserNickname(Long userId) {
        return userRepository.getReferenceById(userId).getNickname();
    }
}
