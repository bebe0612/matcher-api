package kr.kw.matcher.module.article.domain;

import kr.kw.matcher.module.user.domain.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) 
    private String title; // 제목

    @Column(nullable = false, length = 10000) 
    private String content; // 본문

    @ManyToOne(optional = false)
    private User user; // 작성자

    @ToString.Exclude
    @OrderBy("createdDt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 댓글
    
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDt; // 생성일시

    public static Article of(String title, String content, User user) {
        return Article.builder()
                .title(title)
                .content(content)
                .user(user)
                .createdDt(LocalDateTime.now())
                .build();
    }
}
