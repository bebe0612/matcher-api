package kr.kw.matcher.module.article.service;

import kr.kw.matcher.core.exception.ConflictException;
import kr.kw.matcher.core.exception.ForbiddenException;
import kr.kw.matcher.core.exception.NotFoundException;
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
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(ConflictException::new);
        User user = userRepository.findById(dto.getUserId()).orElseThrow(ConflictException::new);

        articleCommentRepository.save(dto.toEntity(article, user));
    }

    // 댓글 업데이트
    public void updateArticleComment(Long articleCommentId, ArticleCommentDto dto) {
        ArticleComment articleComment = articleCommentRepository.findById(articleCommentId).orElseThrow(NotFoundException::new);

        if (!articleComment.getUser().getId().equals(dto.getUserId())) {
            throw new ForbiddenException("댓글 작성자만 수정할 수 있습니다.");
        }

        articleComment.setContent(dto.getContent());
    }

    // 댓글 삭제
    // TODO: soft delete 로 변경 예정
    public void deleteArticleComment(Long articleCommentId, Long userId) {
        articleCommentRepository.deleteByIdAndUser_Id(articleCommentId, userId);
    }
}
