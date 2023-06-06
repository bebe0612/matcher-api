package kr.kw.matcher.module.article.service;

import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.article.domain.ArticleComment;
import kr.kw.matcher.module.article.dto.ArticleCommentDto;
import kr.kw.matcher.module.article.repository.ArticleCommentRepository;
import kr.kw.matcher.module.article.repository.ArticleRepository;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserRepository userRepository;

    // 특정 게시글의 댓글 조회
    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .collect(Collectors.toList());
    }

    // 댓글 저장
    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.getArticleId());
            User user = userRepository.getReferenceById(dto.getUserId());

            articleCommentRepository.save(dto.toEntity(article, user));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장을 실패했습니다. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    // 댓글 업데이트
    public void updateArticleComment(Long articleCommentId, ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(articleCommentId);
            User user = userRepository.getReferenceById(dto.getUserId());

            if (articleComment.getUser().equals(user)) {
                if (dto.getContent() != null) {
                    articleComment.setContent(dto.getContent());
                }
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트를 실패했습니다. 댓글을 찾을 수 없습니다. - dto : {}", dto);
        }
    }

    // 댓글 삭제
    // TODO: soft delete 로 변경 예정
    public void deleteArticleComment(Long articleCommentId, Long userId) {
        articleCommentRepository.deleteByIdAndUser_Id(articleCommentId, userId);
    }
}
