package kr.kw.matcher.core.infra.aws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum S3Path {
    CLUB_HOME("club/home"),
    CLUB_LOGO("club/logo"),
    CLUB_RECRUIT("club/recruit"),
    CLUB_INFO("club/info"),
    PRODUCT("product/image"),
    PHOTO_VERIFICATION("user/verification"),
    USER_PROFILE("user/profile"),
    MEET_IMAGE("meet/image"),
    MEET_PLACE("meet/place"),
    BANNER_IMAGE("common/banner"),
    NOTICE_IMAGE("common/notice"),
    INQUIRY_IMAGE("common/inquiry"),
    EVENT_CARD("common/eventCard"),
    EVENT_ENTRY_IMAGE("common/eventEntry"),
    CHAT_LOCATION("chat/location");
    private final String path;
}
