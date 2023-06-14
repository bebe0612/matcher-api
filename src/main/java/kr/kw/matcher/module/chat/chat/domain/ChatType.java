package kr.kw.matcher.module.chat.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum ChatType {
    TEXT,
    IMAGE,
    LOCATION,
    DATETIME,
    POLL,
    META,
    INFO,
    MEET_CREATED,
    MEET_START,
    MEET_BACK_TO_RECRUIT,
    MEET_MEMBER_NOT_ENOUGH,
    MEET_COMPLETED,
    MEET_BEFORE_30,
    MEET_AFTER_30,
    LOCATION_UPDATED,
    DATETIME_UPDATED;
}
