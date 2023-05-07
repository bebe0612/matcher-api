package kr.kw.matcher.module.article.service;

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

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // SearchType 에 따른 게시글 조회
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) { // 키워드 없이 검색된다면 전체 게시글 조회
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        if (searchType == SearchType.TITLE) {
            return articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
        }
        else if (searchType == SearchType.CONTENT) {
            return articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
        }
        else { // TODO: 작성자 아이디 or 작성자 닉네임으로 검색 고려하기
            return articleRepository.findByUser_IdContaining(searchKeyword, pageable).map(ArticleDto::from);
        }
    }

    // 특정 게시글 조회
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글 조회를 실패했습니다. 게시글을 찾을 수 없습니다. - articleId : " + articleId));
    }

    // 게시글 저장
    public void saveArticle(ArticleDto dto) {
        User user = userRepository.getReferenceById(dto.getUserDto().getId());

        ArticleDto save = ArticleDto.of(dto);
        articleRepository.save(save.toEntity(user));
    }

    // 게시글 업데이트
    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            User user = userRepository.getReferenceById(dto.getUserDto().getId());

            if (article.getUser().equals(user)) { // 게시글 작성자 == 현재 로그인한 사용자
                if (dto.getTitle() != null) {
                    article.setTitle(dto.getTitle());
                }
                if (dto.getContent() != null) {
                    article.setContent(dto.getContent());
                }
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트를 실패했습니다. 게시글을 찾을 수 없습니다. - dto : {}", dto);
        }
    }

    // 게시글 삭제
    // TODO: soft delete 로 변경 예정
    public void deleteArticle(Long articleId, Long userId) {
        articleRepository.deleteByIdAndUser_Id(articleId, userId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }
}