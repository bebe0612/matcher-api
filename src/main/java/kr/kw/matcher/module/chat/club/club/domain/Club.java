package kr.kw.matcher.module.chat.club.club.domain;

import com.campusnow.api.module.club.club.presentation.ClubCreateBody;
import com.campusnow.api.module.club.club.presentation.ClubUpdateBody;
import com.campusnow.api.module.club.club.presentation.requests.ClubProfileUpdateBody;
import com.campusnow.api.module.common.enums.RegionType;
import com.campusnow.api.module.file.file.application.ImageFileDto;
import com.campusnow.api.module.user.user.domain.UnivType;
import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "club")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long presidentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClubType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClubCategoryType categoryType;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @Column(length = 600)
    private String infoText;

    @Column()
    private String imageUrl;

    @Column()
    private String imageThumbUrl;

    @Column()
    private LocalDateTime establishedDt;

    @Column()
    private Long customMemberCount;

    @Column()
    private Point placeLocation;

    @Column()
    private String placeAddress;

    @Column()
    private String placeRoadAddress;

    @Column(nullable = false)
    private Long memberCount;

    @Column(nullable = false)
    private LocalDateTime createdDt;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(nullable = false)
    private Boolean deleted;

    public static Club of(Long userId, ClubCreateBody body, ImageFileDto imageFileDto) {
        return Club.builder()
                .presidentId(userId)
                .type(body.getClubType())
                .categoryType(body.getCategoryType())
                .univType(body.getUnivType())
                .regionType(body.getRegionType())
                .name(body.getName())
                .description(body.getDescription())
                .imageUrl(imageFileDto.getImageUrl())
                .imageThumbUrl(imageFileDto.getImageThumbUrl())
                .memberCount(0L)
                .deleted(false)
                .likeCount(0L)
                .isVerified(false)
                .createdDt(LocalDateTime.now())
                .build();
    }

    public Club updateWith(ClubUpdateBody body) {
        if(body.getName() != null) {
            setName(body.getName());
        }

        if(body.getDescription() != null) {
            setDescription(body.getDescription());
        }

        if(body.getClubType() != null) {
            setType(body.getClubType());
        }

        if(body.getCategoryType() != null) {
            setCategoryType(body.getCategoryType());
        }

        if(body.getUnivType() != null) {
            setUnivType(body.getUnivType());
        }

        if(body.getRegionType() != null) {
            setRegionType(body.getRegionType());
        }

        return this;
    }

    public Club updateInfoWith(ClubProfileUpdateBody body) {
        if(body.getBody() != null) {
            setInfoText(body.getBody());
        }

        return this;
    }
}
