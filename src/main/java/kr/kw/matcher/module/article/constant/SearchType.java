package kr.kw.matcher.module.article.constant;

import lombok.Getter;

public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    ID("유저 ID")
    ;

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
