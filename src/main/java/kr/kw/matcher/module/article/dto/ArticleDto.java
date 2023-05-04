package kr.kw.matcher.module.article.dto;

import kr.kw.matcher.module.article.domain.Article;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    Long id;
    String title;
    String content;
    UserDto userDto;
    LocalDateTime createdDt;

    public static ArticleDto of(String title, String content, UserDto userDto) {
        return ArticleDto.builder()
                .id(null)
                .title(title)
                .content(content)
                .userDto(userDto)
                .createdDt(null)
                .build();
    }

    public static ArticleDto of(Long id, String title, String content, UserDto userDto, LocalDateTime createdDt) {
        return ArticleDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .userDto(userDto)
                .createdDt(createdDt)
                .build();
    }

    // toEntity 를 static 으로 쓸 수 없어서 만든 팩토리 메소드
    public static ArticleDto of(ArticleDto dto) {
        return ArticleDto.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .userDto(dto.getUserDto())
                .build();
    }

    public Article toEntity(User user) {
        return Article.of(title, content, user);
    }

    public static ArticleDto from(Article entity) {
        return ArticleDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                UserDto.from(entity.getUser()),
                entity.getCreatedDt()
        );
    }
}
