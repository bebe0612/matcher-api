package kr.kw.matcher.module.file.domain_image.domain;

import com.campusnow.api.core.infra.aws.S3Path;

public enum DomainType {
    PRODUCT,
    MEET,
    CLUB_IMAGE,
    CLUB_MEMBER,
    CLUB_POSITION,
    CLUB_MEET,
    CLUB_RECRUIT,
    CLUB_INFO,
    CLUB_INVITE,
    CLUB;

    public S3Path toPath() {
        switch (this) {
            case PRODUCT:
                return S3Path.PRODUCT;
            case MEET:
                return S3Path.MEET_IMAGE;
            case CLUB:
                return S3Path.CLUB_HOME;
            case CLUB_RECRUIT:
                return S3Path.CLUB_RECRUIT;
            case CLUB_INFO:
                return S3Path.CLUB_INFO;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
