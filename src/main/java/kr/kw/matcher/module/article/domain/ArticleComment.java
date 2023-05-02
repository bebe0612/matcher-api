package kr.kw.matcher.module.article.domain;

import kr.kw.matcher.module.user.domain.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_comment")
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Article article; // 게시글

    @ManyToOne(optional = false)
    private User user; // 작성자

    @Column(nullable = false, length = 500)
    private String content; // 본문

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDt; // 생성일시

    public static ArticleComment of(Article article, User user, String content) {
        return ArticleComment.builder()
                .article(article)
                .user(user)
                .content(content)
                .createdDt(LocalDateTime.now())
                .build();
    }
}
